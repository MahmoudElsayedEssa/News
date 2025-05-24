package com.example.news.data
import com.example.news.domain.exceptions.*
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

            // Network-specific exceptions
            is TimeoutCancellationException -> NetworkTimeoutException("Request timed out")
            is SocketTimeoutException -> NetworkTimeoutException("Socket timeout occurred")
            is ConnectException -> NetworkTimeoutException("Failed to connect to server")
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

            else -> ApiUnknownException("IO error occurred", exception)
        }
    }

    private fun mapHttpError(code: Int, message: String?): NewsDomainException {
        val errorMessage = message ?: "HTTP error $code"

        return when (code) {
            400 -> InvalidRequestException("Bad request: $errorMessage")
            401 -> AuthenticationException("Authentication failed - check API key")
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

inline fun <T, R> Result<T>.mapResult(transform: (T) -> R): Result<R> {
    return fold(
        onSuccess = { value ->
            try {
                Result.success(transform(value))
            } catch (e: Exception) {
                Result.failure(e)
            }
        },
        onFailure = { Result.failure(it) }
    )
}
