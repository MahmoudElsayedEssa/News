package com.example.news.data.remote.interceptors

import com.example.news.data.remote.exceptions.BadRequestException
import com.example.news.data.remote.exceptions.ForbiddenException
import com.example.news.data.remote.exceptions.RateLimitException
import com.example.news.data.remote.exceptions.ServerErrorException
import com.example.news.data.remote.exceptions.ServiceUnavailableException
import com.example.news.data.remote.exceptions.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject

/**
 * Interceptor for handling and transforming HTTP errors
 */
class ErrorHandlingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Handle HTTP error status codes
        if (!response.isSuccessful) {
            val errorBody = response.body?.string()
            val errorMessage = parseErrorMessage(errorBody) ?: "Unknown API error"

            when (response.code) {
                400 -> throw BadRequestException("Bad request: $errorMessage")
                401 -> throw UnauthorizedException("Invalid API key: $errorMessage")
                403 -> throw ForbiddenException("Access forbidden: $errorMessage")
                429 -> throw RateLimitException("Rate limit exceeded: $errorMessage")
                500 -> throw ServerErrorException("Server error: $errorMessage")
                502, 503, 504 -> throw ServiceUnavailableException("Service unavailable: $errorMessage")
            }
        }

        return response
    }

    private fun parseErrorMessage(errorBody: String?): String? {
        return try {
            errorBody?.let { body ->
                val json = JSONObject(body)
                json.optString("message", null)
            }
        } catch (e: Exception) {
            null
        }
    }
}
