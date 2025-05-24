package com.example.news.data

import com.example.news.domain.exceptions.ApiUnavailableException
import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.AuthenticationException
import com.example.news.domain.exceptions.AuthorizationException
import com.example.news.domain.exceptions.DataNotFoundException
import com.example.news.domain.exceptions.DataValidationException
import com.example.news.domain.exceptions.InvalidRequestException
import com.example.news.domain.exceptions.NetworkTimeoutException
import com.example.news.domain.exceptions.NetworkUnknownException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.exceptions.NoInternetConnectionException
import com.example.news.domain.exceptions.RateLimitExceededException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataErrorMapper @Inject constructor() {

    fun mapToRepositoryError(throwable: Throwable): NewsDomainException {
        return when (throwable) {
            is NewsDomainException -> throwable

            // Handle custom exceptions from interceptors gracefully
            is com.example.news.data.remote.exceptions.BadRequestException ->
                InvalidRequestException("Bad request: ${throwable.message}")
            is com.example.news.data.remote.exceptions.UnauthorizedException ->
                AuthenticationException("Invalid API key: ${throwable.message}")
            is com.example.news.data.remote.exceptions.ForbiddenException ->
                AuthorizationException("Access forbidden: ${throwable.message}")
            is com.example.news.data.remote.exceptions.RateLimitException ->
                RateLimitExceededException("Rate limit exceeded: ${throwable.message}")
            is com.example.news.data.remote.exceptions.ServerErrorException ->
                ApiUnavailableException("Server error: ${throwable.message}")
            is com.example.news.data.remote.exceptions.ServiceUnavailableException ->
                ApiUnavailableException("Service unavailable: ${throwable.message}")

            // Network-specific exceptions
            is TimeoutCancellationException -> NetworkTimeoutException("Request timed out")
            is SocketTimeoutException -> NetworkTimeoutException("Socket timeout occurred")
            is ConnectException -> NoInternetConnectionException("Failed to connect to server")
            is UnknownHostException -> NoInternetConnectionException("Cannot resolve host: ${throwable.message}")

            // IO exceptions - more granular handling
            is IOException -> mapIOException(throwable)

            // HTTP exceptions
            is HttpException -> mapHttpError(throwable.code(), throwable.message())

            // SSL/Security exceptions
            is javax.net.ssl.SSLException -> NoInternetConnectionException("SSL connection failed")

            // Protocol exceptions
            is ProtocolException -> NoInternetConnectionException("Protocol error: ${throwable.message}")

            else -> ApiUnknownException("Unexpected error: ${throwable.javaClass.simpleName}", throwable)
        }
    }

    private fun mapIOException(exception: IOException): NewsDomainException {
        val message = exception.message?.lowercase() ?: ""

        return when {
            message.contains("network") || message.contains("connection") ->
                NoInternetConnectionException("Network connection failed")

            message.contains("timeout") ->
                NetworkTimeoutException("Connection timeout")

            message.contains("host") || message.contains("resolve") ->
                NoInternetConnectionException("Cannot reach server")

            message.contains("permission") ->
                NoInternetConnectionException("Network permission denied")

            else -> NetworkUnknownException("IO error occurred", exception)
        }
    }

    private fun mapHttpError(code: Int, message: String?): NewsDomainException {
        val errorMessage = message ?: "HTTP error $code"

        return when (code) {
            400 -> InvalidRequestException("Bad request: $errorMessage. Please check your parameters.")
            401 -> AuthenticationException("Invalid API key. Please check your NewsAPI key.")
            403 -> AuthorizationException("Access forbidden: $errorMessage")
            404 -> DataNotFoundException("Resource not found")
            409 -> DataValidationException("Conflict: $errorMessage")
            422 -> DataValidationException("Invalid data: $errorMessage")
            429 -> RateLimitExceededException("Rate limit exceeded - try again later")

            in 500..502 -> ApiUnavailableException("Server error: $errorMessage")
            503 -> ApiUnavailableException("Service temporarily unavailable")
            504 -> NetworkTimeoutException("Gateway timeout")

            else -> ApiUnknownException("HTTP $code: $errorMessage")
        }
    }
}