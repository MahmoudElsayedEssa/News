package com.example.souhoolatask.data.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.souhoolatask.data.local.database.NewsDatabase
import com.example.souhoolatask.data.local.entity.ArticleEntity
import com.example.souhoolatask.data.remote.paging.mediator.NewsRemoteMediator
import com.example.souhoolatask.data.repository.datasources.NewsLocalDataSource
import com.example.souhoolatask.data.repository.datasources.NewsRemoteDataSource
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
        val queryKey = "top_headlines_${category}_$country"

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = pageSize
            ),
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
        val queryKey = "search_${query.hashCode()}_$sortBy"

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = pageSize
            ),
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
}
