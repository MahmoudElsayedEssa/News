package com.example.news.data.remote.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.news.data.local.database.NewsDatabase
import com.example.news.data.local.entity.ArticleEntity
import com.example.news.data.local.entity.RemoteKeyEntity
import com.example.news.data.local.mappers.EntityMapper
import com.example.news.domain.exceptions.NetworkTimeoutException
import com.example.news.data.remote.dtos.ArticleDto
import com.example.news.data.repository.datasources.NewsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

/**
 * The brain of Paging 3 - coordinates network + database
 * This is where the magic happens!
 */

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val queryKey: String,
    private val query: String?,
    private val category: String?,
    private val country: String?,
    private val sortBy: String?,
    private val remoteDataSource: NewsRemoteDataSource,
    private val database: NewsDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteMediator<Int, ArticleEntity>() {

    private val articlesDao = database.articlesDao()
    private val remoteKeysDao = database.remoteKeysDao()

    companion object {
        private const val NETWORK_TIMEOUT_MS = 30_000L
        private const val CACHE_TIMEOUT_HOURS = 1L
        private const val MAX_RETRY_ATTEMPTS = 3
    }

    override suspend fun initialize(): InitializeAction {
        return try {
            val cacheTimeout = TimeUnit.HOURS.toMillis(CACHE_TIMEOUT_HOURS)
            val lastCacheTime = getLastCacheTime()

            if (System.currentTimeMillis() - lastCacheTime < cacheTimeout) {
                InitializeAction.SKIP_INITIAL_REFRESH
            } else {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            }
        } catch (e: Exception) {
            // If we can't determine cache age, refresh to be safe
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult = withContext(ioDispatcher) {

        var attempt = 0
        var lastException: Exception? = null

        while (attempt < MAX_RETRY_ATTEMPTS) {
            try {
                return@withContext performLoad(loadType, state)
            } catch (e: TimeoutCancellationException) {
                lastException = NetworkTimeoutException("Request timed out (attempt ${attempt + 1})")
                attempt++
                if (attempt < MAX_RETRY_ATTEMPTS) {
                    kotlinx.coroutines.delay(1000L * attempt) // Exponential backoff
                }
            } catch (e: Exception) {
                return@withContext MediatorResult.Error(e)
            }
        }

        MediatorResult.Error(lastException ?: Exception("Unknown error after retries"))
    }

    private suspend fun performLoad(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItemSafely(state)
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                remoteKey.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        val response = withTimeout(NETWORK_TIMEOUT_MS) {
            if (query.isNullOrBlank()) {
                remoteDataSource.getTopHeadlines(
                    query = null,
                    category = category,
                    country = country,
                    page = page,
                    pageSize = state.config.pageSize
                )
            } else {
                remoteDataSource.searchEverything(
                    query = query,
                    sortBy = sortBy,
                    page = page,
                    pageSize = state.config.pageSize
                )
            }
        }

        return response.fold(
            onSuccess = { newsResponse ->
                saveToDatabase(newsResponse.articles, page, loadType)
            },
            onFailure = { error ->
                MediatorResult.Error(error)
            }
        )
    }

    private suspend fun saveToDatabase(
        articles: List<ArticleDto>,
        page: Int,
        loadType: LoadType
    ): MediatorResult {
        val endOfPaginationReached = articles.isEmpty()

        return try {
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articlesDao.deleteArticlesByQuery(queryKey)
                    remoteKeysDao.deleteRemoteKeysByQuery(queryKey)
                }

                val articleEntities = articles.mapNotNull { dto ->
                    try {
                        EntityMapper.mapDtoToEntity(dto, queryKey)
                    } catch (e: Exception) {
                        // Log error but continue with other articles
                        null
                    }
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = articleEntities.map { entity ->
                    RemoteKeyEntity(
                        queryKey = queryKey,
                        articleId = entity.id,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        createdAt = System.currentTimeMillis()
                    )
                }

                if (articleEntities.isNotEmpty()) {
                    articlesDao.insertArticles(articleEntities)
                }
                if (remoteKeys.isNotEmpty()) {
                    remoteKeysDao.insertRemoteKeys(remoteKeys)
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItemSafely(
        state: PagingState<Int, ArticleEntity>
    ): RemoteKeyEntity? {
        return try {
            state.pages
                .lastOrNull { it.data.isNotEmpty() }
                ?.data
                ?.lastOrNull()
                ?.let { article ->
                    remoteKeysDao.getRemoteKey(queryKey, article.id)
                }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun getLastCacheTime(): Long {
        return try {
            // Get the most recent cache time for this query
            remoteKeysDao.getLastCacheTime(queryKey) ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
}
