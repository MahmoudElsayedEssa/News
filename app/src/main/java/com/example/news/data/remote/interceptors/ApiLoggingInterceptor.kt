package com.example.news.data.remote.interceptors

import android.util.Log
import com.example.news.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Logging interceptor for debugging (only in debug builds)
 */
class ApiLoggingInterceptor : Interceptor {

    private val logger = HttpLoggingInterceptor { message ->
        Log.d("NewsAPI", message)
    }

    init {
        logger.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return logger.intercept(chain)
    }
}