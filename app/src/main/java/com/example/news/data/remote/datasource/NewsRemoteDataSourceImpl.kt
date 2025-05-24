package com.example.news.data.remote.datasource

import com.example.news.data.DataErrorMapper
import com.example.news.data.remote.api.NewsApiService
import com.example.news.data.remote.dtos.NewsResponseDto
import com.example.news.data.remote.dtos.SourcesResponseDto
import com.example.news.data.repository.NewsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of remote data source using Retrofit
 * Handles network calls, error mapping, and response processing
 */
@Singleton
class NewsRemoteDataSourceImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val errorMapper: DataErrorMapper
) : NewsRemoteDataSource {

    override suspend fun getTopHeadlines(
        query: String?,
        sources: List<String>?,
        category: String?,
        country: String?,
        pageSize: Int,
        page: Int
    ): Result<NewsResponseDto> {
        return safeApiCall {
            apiService.getTopHeadlines(
                query = query,
                sources = sources?.joinToString(","),
                category = category,
                country = country,
                pageSize = pageSize,
                page = page
            )
        }
    }

    override suspend fun searchEverything(
        query: String,
        queryInTitle: String?,
        sources: List<String>?,
        domains: List<String>?,
        excludeDomains: List<String>?,
        from: String?,
        to: String?,
        language: String?,
        sortBy: String?,
        pageSize: Int,
        page: Int
    ): Result<NewsResponseDto> {
        return safeApiCall {
            apiService.searchEverything(
                query = query,
                queryInTitle = queryInTitle,
                sources = sources?.joinToString(","),
                domains = domains?.joinToString(","),
                excludeDomains = excludeDomains?.joinToString(","),
                from = from,
                to = to,
                language = language,
                sortBy = sortBy,
                pageSize = pageSize,
                page = page
            )
        }
    }

    override suspend fun getSources(
        category: String?,
        language: String?,
        country: String?
    ): Result<SourcesResponseDto> {
        return safeApiCall {
            apiService.getSources(
                category = category,
                language = language,
                country = country
            )
        }
    }

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Result<T> = withContext(Dispatchers.IO) {
        try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(errorMapper.mapToRepositoryError(
                        Exception("Response body is null")
                    ))
                }
            } else {
                Result.failure(errorMapper.mapToRepositoryError(
                    HttpException(response)
                ))
            }
        } catch (e: Exception) {
            Result.failure(errorMapper.mapToRepositoryError(e))
        }
    }
}