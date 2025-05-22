package com.example.souhoolatask.data.remote.datasource

import com.example.souhoolatask.data.remote.api.NewsApiService
import com.example.souhoolatask.data.remote.dtos.NewsResponseDto
import com.example.souhoolatask.data.remote.dtos.SourcesResponseDto
import com.example.souhoolatask.data.remote.exceptions.NetworkConnectionException
import com.example.souhoolatask.data.remote.exceptions.NetworkHostException
import com.example.souhoolatask.data.remote.exceptions.NoConnectivityException
import com.example.souhoolatask.data.remote.exceptions.RateLimitException
import com.example.souhoolatask.data.remote.exceptions.ServerErrorException
import com.example.souhoolatask.data.remote.exceptions.ServiceUnavailableException
import com.example.souhoolatask.data.remote.exceptions.UnauthorizedException
import com.example.souhoolatask.data.repository.datasources.NewsRemoteDataSource
import com.example.news.domain.exceptions.ApiUnavailableException
import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.AuthenticationException
import com.example.news.domain.exceptions.AuthorizationException
import com.example.news.domain.exceptions.DataParsingException
import com.example.news.domain.exceptions.InvalidRequestException
import com.example.news.domain.exceptions.NetworkTimeoutException
import com.example.news.domain.exceptions.NetworkUnknownException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.exceptions.NoInternetConnectionException
import com.example.news.domain.exceptions.RateLimitExceededException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of remote data source using Retrofit
 * Handles network calls, error mapping, and response processing
 */
@Singleton
class NewsRemoteDataSourceImpl @Inject constructor(
    private val apiService: NewsApiService
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
        category: String?, language: String?, country: String?
    ): Result<SourcesResponseDto> {
        return safeApiCall {
            apiService.getSources(
                category = category, language = language, country = country
            )
        }
    }

    /**
     * Safe API call wrapper that handles errors and maps them to domain exceptions
     */
    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(DataParsingException("Response body is null"))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(mapHttpError(response.code(), errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(mapNetworkException(e))
        }
    }

    /**
     * Map HTTP status codes to domain exceptions
     */
    private fun mapHttpError(code: Int, message: String): NewsDomainException {
        return when (code) {
            400 -> InvalidRequestException("Bad request: $message")
            401 -> AuthenticationException("Invalid API key: $message")
            403 -> AuthorizationException("Access forbidden: $message")
            429 -> RateLimitExceededException("Rate limit exceeded: $message")
            500, 502, 503, 504 -> ApiUnavailableException("Service unavailable: $message")
            else -> ApiUnknownException("HTTP $code: $message")
        }
    }

    /**
     * Map network exceptions to domain exceptions
     */
    private fun mapNetworkException(exception: Exception): NewsDomainException {
        return when (exception) {
            is NoConnectivityException -> NoInternetConnectionException(
                exception.message ?: "No internet connection"
            )

            is NetworkTimeoutException -> NetworkTimeoutException(
                exception.message ?: "Request timed out"
            )

            is NetworkConnectionException -> NetworkUnknownException(
                exception.message ?: "Network connection failed", exception
            )

            is NetworkHostException -> NetworkUnknownException(
                exception.message ?: "Host not found", exception
            )

            is UnauthorizedException -> AuthenticationException(
                exception.message ?: "Authentication failed"
            )

            is RateLimitException -> RateLimitExceededException(
                exception.message ?: "Rate limit exceeded"
            )

            is ServerErrorException -> ApiUnavailableException(exception.message ?: "Server error")
            is ServiceUnavailableException -> ApiUnavailableException(
                exception.message ?: "Service unavailable"
            )

            else -> ApiUnknownException("Unknown network error: ${exception.message}", exception)
        }
    }
}
