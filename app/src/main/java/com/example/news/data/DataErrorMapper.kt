package com.example.news.data
import com.example.news.domain.exceptions.*
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataErrorMapper @Inject constructor() {

    fun mapToRepositoryError(throwable: Throwable): NewsDomainException {
        return when (throwable) {
            is NewsDomainException -> throwable
            is TimeoutCancellationException -> NetworkTimeoutException("Request timed out")
            is SocketTimeoutException -> NetworkTimeoutException("Socket timeout")
            is UnknownHostException -> NoInternetConnectionException("Host not found")
            is IOException -> NoInternetConnectionException("Network connection failed")
            is HttpException -> mapHttpError(throwable.code(), throwable.message())
            else -> ApiUnknownException("Unknown error occurred", throwable)
        }
    }

    private fun mapHttpError(code: Int, message: String?): NewsDomainException {
        val errorMessage = message ?: "Unknown HTTP error"
        return when (code) {
            400 -> InvalidRequestException("Bad request: $errorMessage")
            401 -> AuthenticationException("Invalid API key")
            403 -> AuthorizationException("Access forbidden")
            429 -> RateLimitExceededException("Rate limit exceeded")
            in 500..599 -> ApiUnavailableException("Service unavailable: $errorMessage")
            else -> ApiUnknownException("HTTP $code: $errorMessage")
        }
    }
}

inline fun <T, R> Result<T>.mapResult(transform: (T) -> R): Result<R> {
    return fold(
        onSuccess = { Result.success(transform(it)) },
        onFailure = { Result.failure(it) }
    )
}