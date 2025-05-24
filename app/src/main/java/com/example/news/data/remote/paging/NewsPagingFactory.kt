package com.example.news.data.remote.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news.data.remote.paging.fetcher.SearchEverythingFetcher
import com.example.news.data.remote.paging.fetcher.TopHeadlinesFetcher
import com.example.news.data.repository.NewsRemoteDataSource
import com.example.news.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsPagingFactory @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource
) {

    fun createTopHeadlinesPager(
        category: String? = null,
        country: String? = null,
        query: String? = null,
        pageSize: Int = 20
    ): Flow<PagingData<Article>> {

        val queryKey = "top_headlines_${category}_${country}_${query?.hashCode() ?: "null"}"

        val fetcher = TopHeadlinesFetcher(
            queryKey = queryKey,
            apiPageSize = pageSize,
            remoteDataSource = remoteDataSource,
            query = query,
            category = category,
            country = country
        )

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = pageSize * 2
            ),
            pagingSourceFactory = { ArticleNetworkPagingSource(fetcher) }
        ).flow
    }

    fun createSearchPager(
        query: String,
        sortBy: String? = "relevancy",
        pageSize: Int = 20
    ): Flow<PagingData<Article>> {

        require(query.isNotBlank()) { "Search query cannot be blank" }
        require(query.length >= 2) { "Search query must be at least 2 characters" }

        val sanitizedQuery = query.trim()
        val queryKey = "search_${sanitizedQuery.hashCode()}_${sortBy.hashCode()}"

        val fetcher = SearchEverythingFetcher(
            queryKey = queryKey,
            apiPageSize = pageSize,
            remoteDataSource = remoteDataSource,
            searchQuery = sanitizedQuery,
            sortBy = sortBy
        )

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = pageSize * 2
            ),
            pagingSourceFactory = { ArticleNetworkPagingSource(fetcher) }
        ).flow
    }
}