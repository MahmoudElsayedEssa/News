package com.example.souhoolatask.data.repository.datasources

import androidx.paging.PagingSource
import com.example.souhoolatask.data.local.entity.ArticleEntity

/**
 * Local data source interface for Room operations
 */
interface NewsLocalDataSource {
    fun getArticlesPagingSource(queryKey: String): PagingSource<Int, ArticleEntity>
    suspend fun getArticlesByQuery(queryKey: String): List<ArticleEntity>
    suspend fun cacheArticles(queryKey: String, articles: List<ArticleEntity>)
    suspend fun refreshArticles(queryKey: String, articles: List<ArticleEntity>)
    suspend fun getArticleByUrl(url: String): ArticleEntity?
    suspend fun clearExpiredCache()
    suspend fun getCacheSize(queryKey: String): Int
}
