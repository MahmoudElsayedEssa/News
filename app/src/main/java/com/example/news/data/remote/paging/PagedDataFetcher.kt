package com.example.news.data.remote.paging.fetcher // Or your chosen package

import com.example.news.data.remote.dtos.NewsResponseDto
import com.example.news.data.repository.NewsRemoteDataSource

/**
 * Interface for abstracting the data fetching logic for the RemoteMediator.
 */
interface PagedDataFetcher {
    /**
     * A unique key representing the specific query this fetcher handles.
     * Used for local caching and identifying data in the database.
     */
    val queryKey: String

    /**
     * The page size this fetcher is configured to request from the API.
     * This should align with PagingConfig.pageSize.
     */
    val apiPageSize: Int

    /**
     * Fetches a specific page of data.
     * @param page The page number to fetch.
     * @param pageSize The number of items to fetch per page (Note: typically consistent with apiPageSize).
     * @return A Result containing the NewsResponseDto or an error.
     */
    suspend fun fetch(page: Int, pageSize: Int): Result<NewsResponseDto>
}

class TopHeadlinesFetcher(
    override val queryKey: String,
    override val apiPageSize: Int,
    private val remoteDataSource: NewsRemoteDataSource,
    private val query: String?,
    private val category: String, // Expect non-null due to defaulting in factory
    private val country: String,  // Expect non-null due to defaulting in factory
    private val sources: List<String>?
) : PagedDataFetcher {
    override suspend fun fetch(page: Int, pageSize: Int): Result<NewsResponseDto> {
        // The pageSize parameter here comes from PagingState's config.
        // We use apiPageSize which was set during factory configuration to ensure consistency.
        return remoteDataSource.getTopHeadlines(
            query = query,
            sources = sources,
            category = category,
            country = country,
            page = page,
            pageSize = apiPageSize // Use the pageSize this fetcher was configured with
        )
    }
}

class SearchEverythingFetcher(
    override val queryKey: String,
    override val apiPageSize: Int,
    private val remoteDataSource: NewsRemoteDataSource,
    private val searchQuery: String, // Renamed from 'query' to avoid confusion
    private val sortBy: String?,
    private val sources: List<String>?,
    private val fromDate: String?,
    private val toDate: String?
) : PagedDataFetcher {
    override suspend fun fetch(page: Int, pageSize: Int): Result<NewsResponseDto> {
        return remoteDataSource.searchEverything(
            query = searchQuery,
            sources = sources,
            sortBy = sortBy,
            from = fromDate,
            to = toDate,
            page = page,
            pageSize = apiPageSize // Use the pageSize this fetcher was configured with
        )
    }
}