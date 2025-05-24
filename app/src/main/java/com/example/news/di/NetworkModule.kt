package com.example.news.di

import android.content.Context
import com.example.news.BuildConfig
import com.example.news.data.remote.interceptors.ApiLoggingInterceptor
import com.example.news.data.remote.api.NewsApiService
import com.example.news.data.remote.interceptors.AuthenticationInterceptor
import com.example.news.data.remote.interceptors.CacheInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Hilt module for network-related dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provide NewsAPI key from BuildConfig or local.properties
     */
    @Provides
    @Singleton
    @Named("api_key")
    fun provideApiKey(): String {
        return BuildConfig.NEWS_API_KEY
    }

    /**
     * Provide Gson instance for JSON serialization
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").setLenient().create()
    }

    /**
     * Provide authentication interceptor
     */
    @Provides
    @Singleton
    fun provideAuthInterceptor(@Named("api_key") apiKey: String): AuthenticationInterceptor {
        return AuthenticationInterceptor(apiKey)
    }


    /**
     * Provide logging interceptor
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): ApiLoggingInterceptor {
        return ApiLoggingInterceptor()
    }

    /**
     * Provide cache interceptor
     */
    @Provides
    @Singleton
    fun provideCacheInterceptor(): CacheInterceptor {
        return CacheInterceptor()
    }

    /**
     * Provide HTTP cache
     */
    @Provides
    @Singleton
    fun provideHttpCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, cacheSize.toLong())
    }

    /**
     * Provide configured OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthenticationInterceptor,
        loggingInterceptor: ApiLoggingInterceptor,
        cacheInterceptor: CacheInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(cacheInterceptor)
            .addInterceptor(loggingInterceptor).cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true).build()
    }

    /**
     * Provide Retrofit instance
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient, gson: Gson
    ): Retrofit {
        return Retrofit.Builder().baseUrl(NewsApiService.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    /**
     * Provide NewsApiService
     */
    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}
