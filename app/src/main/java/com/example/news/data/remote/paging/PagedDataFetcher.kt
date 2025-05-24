package com.example.news.data.remote.paging.fetcher

import com.example.news.data.remote.dtos.NewsResponseDto
import com.example.news.data.repository.NewsRemoteDataSource

interface PagedDataFetcher {
    val queryKey: String
    val apiPageSize: Int
    suspend fun fetch(page: Int, pageSize: Int): Result<NewsResponseDto>
}

class TopHeadlinesFetcher(
    override val queryKey: String,
    override val apiPageSize: Int,
    private val remoteDataSource: NewsRemoteDataSource,
    private val query: String?,
    private val category: String?,
    private val country: String?
) : PagedDataFetcher {

    override suspend fun fetch(page: Int, pageSize: Int): Result<NewsResponseDto> {
        return remoteDataSource.getTopHeadlines(
            query = query,
            category = category,
            country = country,
            page = page,
            pageSize = apiPageSize
        )
    }
}

class SearchEverythingFetcher(
    override val queryKey: String,
    override val apiPageSize: Int,
    private val remoteDataSource: NewsRemoteDataSource,
    private val searchQuery: String,
    private val sortBy: String?
) : PagedDataFetcher {

    override suspend fun fetch(page: Int, pageSize: Int): Result<NewsResponseDto> {
        return remoteDataSource.searchEverything(
            query = searchQuery,
            sortBy = sortBy,
            page = page,
            pageSize = apiPageSize
        )
    }
}