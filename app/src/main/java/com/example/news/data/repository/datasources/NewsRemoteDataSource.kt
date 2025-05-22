package com.example.souhoolatask.data.repository.datasources

import com.example.souhoolatask.data.remote.dtos.NewsResponseDto
import com.example.souhoolatask.data.remote.dtos.SourcesResponseDto

/**
 * Remote data source interface for news data
 * Clean abstraction layer between repository and network implementation
 */
interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(
        query: String? = null,
        sources: List<String>? = null,
        category: String? = null,
        country: String? = null,
        pageSize: Int = 20,
        page: Int = 1
    ): Result<NewsResponseDto>

    suspend fun searchEverything(
        query: String,
        queryInTitle: String? = null,
        sources: List<String>? = null,
        domains: List<String>? = null,
        excludeDomains: List<String>? = null,
        from: String? = null,
        to: String? = null,
        language: String? = null,
        sortBy: String? = null,
        pageSize: Int = 20,
        page: Int = 1
    ): Result<NewsResponseDto>

    suspend fun getSources(
        category: String? = null,
        language: String? = null,
        country: String? = null
    ): Result<SourcesResponseDto>
}