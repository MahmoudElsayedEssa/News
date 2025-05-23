package com.example.news.data.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news.data.local.database.NewsDatabase
import com.example.news.data.local.entity.ArticleEntity
import com.example.news.data.remote.paging.mediator.NewsRemoteMediator
import com.example.news.data.repository.datasources.NewsLocalDataSource
import com.example.news.data.repository.datasources.NewsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Factory to create different types of paging streams
 */
@Singleton
class NewsPagingFactory @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource,
    private val database: NewsDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun createTopHeadlinesPager(
        category: String? = null,
        country: String? = null,
        pageSize: Int = 20
    ): Flow<PagingData<ArticleEntity>> {
        val queryKey = "top_headlines_${category}_${country}"

        return Pager(
            config = createPagingConfig(pageSize),
            remoteMediator = NewsRemoteMediator(
                queryKey = queryKey,
                query = null,
                category = category,
                country = country,
                sortBy = null,
                remoteDataSource = remoteDataSource,
                database = database
            ),
            pagingSourceFactory = { localDataSource.getArticlesPagingSource(queryKey) }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun createSearchPager(
        query: String,
        sortBy: String? = "relevancy",
        pageSize: Int = 20
    ): Flow<PagingData<ArticleEntity>> {
        val queryKey = "search_${query.hashCode()}_${sortBy.hashCode()}"

        return Pager(
            config = createPagingConfig(pageSize),
            remoteMediator = NewsRemoteMediator(
                queryKey = queryKey,
                query = query,
                category = null,
                country = null,
                sortBy = sortBy,
                remoteDataSource = remoteDataSource,
                database = database
            ),
            pagingSourceFactory = { localDataSource.getArticlesPagingSource(queryKey) }
        ).flow
    }

    private fun createPagingConfig(pageSize: Int): PagingConfig {
        return PagingConfig(
            pageSize = pageSize,
            prefetchDistance = maxOf(1, pageSize / 4), // Prefetch 25% of page size
            enablePlaceholders = false,
            initialLoadSize = pageSize,
            maxSize = pageSize * 10 // Limit memory usage
        )
    }
}
