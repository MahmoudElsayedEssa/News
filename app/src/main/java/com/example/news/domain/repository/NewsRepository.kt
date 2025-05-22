package com.example.news.domain.repository

import androidx.paging.PagingData
import com.example.souhoolatask.data.remote.configuration.RemoteDataConfig.DEFAULT_PAGE_SIZE
import com.example.news.domain.model.Article
import com.example.news.domain.model.ArticlesPage
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.model.values.SourceId
import kotlinx.coroutines.flow.Flow

/**
 * Main repository interface for news data access
 * Follows Repository pattern with clean abstractions
 */
interface NewsRepository {


    /**
     * Get top headlines with traditional pagination - works with your existing use case
     */
    suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        query: String? = null,
        sortBy: SortBy = SortBy.PUBLISHED_AT,
        category: NewsCategory? = null,
        country: Country? = null
    ): Result<ArticlesPage>

    /**
     * Search articles with traditional pagination - for search use case
     */
    suspend fun searchEverything(
        query: String,
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        sortBy: SortBy = SortBy.RELEVANCY,
        sources: List<SourceId>? = null,
        fromDate: String? = null,
        toDate: String? = null
    ): Result<ArticlesPage>

    /**
     * Get available news sources - works with your existing use case
     */
    suspend fun getSources(
        category: NewsCategory? = null,
        country: Country? = null
    ): Result<List<Source>>


    /**
     * Get paginated top headlines with offline support (for infinite scroll UI)
     */
    fun getTopHeadlinesPaging(
        category: NewsCategory? = null,
        country: Country? = null,
        query: String? = null,
        sortBy: SortBy = SortBy.PUBLISHED_AT,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Article>>

    /**
     * Get paginated search results with offline support (for infinite scroll UI)
     */
    fun searchArticlesPaging(
        query: String,
        sortBy: SortBy = SortBy.RELEVANCY,
        sources: List<SourceId>? = null,
        fromDate: String? = null,
        toDate: String? = null,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Article>>


    suspend fun refresh()
    suspend fun clearCache()

}
