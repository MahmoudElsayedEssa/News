package com.example.news.domain.repository

import androidx.paging.PagingData
import com.example.news.domain.model.Article
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import kotlinx.coroutines.flow.Flow

/**
 * Main repository interface for news data access
 * Follows Repository pattern with clean abstractions
 */

interface NewsRepository {

    fun getTopHeadlinesPaging(
        category: NewsCategory? = null,
        country: Country? = null,
        query: String? = null,
        pageSize: Int = 20
    ): Flow<PagingData<Article>>

    fun searchArticlesPaging(
        query: String, sortBy: SortBy = SortBy.RELEVANCY, pageSize: Int = 20
    ): Flow<PagingData<Article>>

    suspend fun getArticleByUrl(url: String): Result<Article>

    suspend fun getSources(
        category: NewsCategory? = null, country: Country? = null
    ): Result<List<Source>>

    suspend fun refresh()
    suspend fun clearCache()
    suspend fun isOnline(): Boolean
}