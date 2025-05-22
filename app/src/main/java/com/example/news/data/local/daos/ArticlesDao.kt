package com.example.news.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.souhoolatask.data.local.entity.ArticleEntity

/**
 * DAO for articles with Paging 3 support
 */
@Dao
interface ArticlesDao {

    // Existing methods...
    @Query("SELECT * FROM articles WHERE query_key = :queryKey ORDER BY published_at DESC")
    fun getArticlesPagingSource(queryKey: String): PagingSource<Int, ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles WHERE query_key = :queryKey")
    suspend fun deleteArticlesByQuery(queryKey: String)

    @Query("DELETE FROM articles WHERE cached_at < :expireTime")
    suspend fun deleteExpiredArticles(expireTime: Long)

    /**
     * Get articles by query key as a list (not paging source)
     */
    @Query("SELECT * FROM articles WHERE query_key = :queryKey ORDER BY published_at DESC")
    suspend fun getArticlesByQuery(queryKey: String): List<ArticleEntity>

    /**
     * Get article by URL
     */
    @Query("SELECT * FROM articles WHERE url = :url")
    suspend fun getArticleByUrl(url: String): ArticleEntity?

    /**
     * Get count of articles for a query
     */
    @Query("SELECT COUNT(*) FROM articles WHERE query_key = :queryKey")
    suspend fun getArticleCount(queryKey: String): Int

    /**
     * Get all cached articles (useful for cleanup)
     */
    @Query("SELECT * FROM articles ORDER BY cached_at DESC")
    suspend fun getAllCachedArticles(): List<ArticleEntity>

    /**
     * Delete all articles
     */
    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()

    /**
     * Transaction method for refresh
     */
    @Transaction
    suspend fun refreshArticles(queryKey: String, articles: List<ArticleEntity>) {
        deleteArticlesByQuery(queryKey)
        insertArticles(articles)
    }
}
