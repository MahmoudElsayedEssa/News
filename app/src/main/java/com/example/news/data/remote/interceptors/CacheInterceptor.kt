package com.example.souhoolatask.data.remote.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

/**
 * Cache interceptor for offline support and performance
 */
class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Cache successful responses for 5 minutes
        val cacheControl = CacheControl.Builder()
            .maxAge(5, TimeUnit.MINUTES)
            .build()

        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}
