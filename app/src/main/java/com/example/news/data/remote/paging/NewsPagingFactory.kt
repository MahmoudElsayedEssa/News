package com.example.news.data.remote.paging

import androidx.paging.ExperimentalPagingApi
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

    @OptIn(ExperimentalPagingApi::class)
    fun createTopHeadlinesPager(
        category: String? = null,
        country: String? = null,
        query: String? = null,
        pageSize: Int = 20
    ): Flow<PagingData<Article>> {

        val safeCategory = category ?: "general" //
        val safeCountry = country ?: "us" //
        val queryKeyForFetcherIdentification = "top_headlines_${safeCategory}_${safeCountry}_${query?.hashCode() ?: "null"}" //

        val fetcher = TopHeadlinesFetcher(
            queryKey = queryKeyForFetcherIdentification,
            apiPageSize = pageSize,
            remoteDataSource = remoteDataSource,
            query = query,
            category = safeCategory,
            country = safeCountry,
            sources = null
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

    @OptIn(ExperimentalPagingApi::class)
    fun createSearchPager(
        query: String,
        sortBy: String? = "relevancy",
        sources: List<String>? = null,
        fromDate: String? = null,
        toDate: String? = null,
        pageSize: Int = 20
    ): Flow<PagingData<Article>> {

        require(query.isNotBlank()) { "Search query cannot be blank" } //
        require(query.length >= 2) { "Search query must be at least 2 characters" } //

        val sanitizedQuery = query.trim() //
        val queryKeyForFetcherIdentification = "search_${sanitizedQuery.hashCode()}_${sortBy.hashCode()}_${sources?.hashCode() ?: 0}" //

        val fetcher = SearchEverythingFetcher(
            queryKey = queryKeyForFetcherIdentification,
            apiPageSize = pageSize,
            remoteDataSource = remoteDataSource,
            searchQuery = sanitizedQuery,
            sortBy = sortBy,
            sources = sources,
            fromDate = fromDate,
            toDate = toDate
        )

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = pageSize * 2
            ),
            pagingSourceFactory = { ArticleNetworkPagingSource(fetcher) } // Directly use the network PagingSource
        ).flow
    }
}