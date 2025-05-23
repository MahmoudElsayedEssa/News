package com.example.news.data.remote.api

import com.example.news.data.remote.dtos.NewsResponseDto
import com.example.news.data.remote.dtos.SourcesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service interface for NewsAPI endpoints
 * Clean, focused interface with proper error handling
 */
interface NewsApiService {

    /**
     * Get top headlines with comprehensive filtering options
     */
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("q") query: String? = null,
        @Query("sources") sources: String? = null,
        @Query("category") category: String? = null,
        @Query("country") country: String? = null,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): Response<NewsResponseDto>

    /**
     * Search everything with advanced filtering
     */
    @GET("v2/everything")
    suspend fun searchEverything(
        @Query("q") query: String,
        @Query("qInTitle") queryInTitle: String? = null,
        @Query("sources") sources: String? = null,
        @Query("domains") domains: String? = null,
        @Query("excludeDomains") excludeDomains: String? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): Response<NewsResponseDto>

    /**
     * Get available news sources
     */
    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String? = null,
        @Query("language") language: String? = null,
        @Query("country") country: String? = null
    ): Response<SourcesResponseDto>

    companion object {
        const val BASE_URL = "https://newsapi.org/"
    }
}
