package com.example.souhoolatask.data.remote.interceptors

import okhttp3.Interceptor

/**
 * Interceptor for adding API key authentication to all requests
 */
class AuthenticationInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()

        // Add API key to all requests
        val urlWithApiKey = originalRequest.url.newBuilder()
            .addQueryParameter("apiKey", apiKey)
            .build()

        val authenticatedRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
