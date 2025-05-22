package com.example.souhoolatask.data.remote.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.souhoolatask.data.local.database.NewsDatabase
import com.example.souhoolatask.data.local.entity.ArticleEntity
import com.example.souhoolatask.data.local.entity.RemoteKeyEntity
import com.example.souhoolatask.data.repository.datasources.NewsRemoteDataSource

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
    private val database: NewsDatabase
) : RemoteMediator<Int, ArticleEntity>() {

    private val articlesDao = database.articlesDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            // Determine which page to load
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // Load from network based on query type
            val response = if (query.isNullOrBlank()) {
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

            // Process the response
            response.fold(
                onSuccess = { newsResponse ->
                    val articles = newsResponse.articles
                    val endOfPaginationReached = articles.isEmpty()

                    // Database transaction - all or nothing
                    database.withTransaction {
                        // Clear on refresh
                        if (loadType == LoadType.REFRESH) {
                            articlesDao.deleteArticlesByQuery(queryKey)
                            remoteKeysDao.deleteRemoteKeysByQuery(queryKey)
                        }

                        // Convert DTOs to entities
                        val articleEntities = articles.map { dto ->
                            ArticleEntity(
                                url = dto.url,
                                title = dto.title,
                                description = dto.description,
                                content = dto.content,
                                imageUrl = dto.urlToImage,
                                publishedAt = dto.publishedAt,
                                sourceId = dto.source.id,
                                sourceName = dto.source.name,
                                author = dto.author,
                                queryKey = queryKey
                            )
                        }

                        // Create remote keys (pagination bookmarks)
                        val prevKey = if (page == 1) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1
                        val remoteKeys = articles.map { article ->
                            RemoteKeyEntity(
                                queryKey = queryKey,
                                articleUrl = article.url,
                                prevKey = prevKey,
                                nextKey = nextKey
                            )
                        }

                        // Save everything
                        articlesDao.insertArticles(articleEntities)
                        remoteKeysDao.insertRemoteKeys(remoteKeys)
                    }

                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                },
                onFailure = { error ->
                    MediatorResult.Error(error)
                }
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    // Helper to get pagination info for last item
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { article ->
            remoteKeysDao.getRemoteKey(queryKey, article.url)
        }
    }
}
