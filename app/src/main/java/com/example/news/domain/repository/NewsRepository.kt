package com.example.news.domain.repository

import androidx.paging.PagingData
import com.example.news.data.remote.configuration.RemoteDataConfig.DEFAULT_PAGE_SIZE
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

    /**
     * Get available news sources
     */
    suspend fun getSources(
        category: NewsCategory? = null,
        country: Country? = null
    ): Result<List<Source>>

    suspend fun refresh()
    suspend fun clearCache()

    // Health check
    suspend fun isOnline(): Boolean
}
