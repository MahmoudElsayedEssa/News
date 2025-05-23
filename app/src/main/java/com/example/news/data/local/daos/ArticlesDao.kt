package com.example.news.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.news.data.local.entity.ArticleEntity

/**
 * DAO for articles with Paging 3 support
 */
@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles WHERE query_key = :queryKey ORDER BY published_at DESC")
    fun getArticlesPagingSource(queryKey: String): PagingSource<Int, ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles WHERE query_key = :queryKey")
    suspend fun deleteArticlesByQuery(queryKey: String)

    @Query("DELETE FROM articles WHERE cached_at < :expireTime")
    suspend fun deleteExpiredArticles(expireTime: Long)

    @Query("SELECT * FROM articles WHERE query_key = :queryKey ORDER BY published_at DESC")
    suspend fun getArticlesByQuery(queryKey: String): List<ArticleEntity>

    @Query("SELECT * FROM articles WHERE url = :url LIMIT 1")
    suspend fun getArticleByUrl(url: String): ArticleEntity?

    @Query("SELECT COUNT(*) FROM articles WHERE query_key = :queryKey")
    suspend fun getArticleCount(queryKey: String): Int

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()

    @Transaction
    suspend fun refreshArticles(queryKey: String, articles: List<ArticleEntity>) {
        deleteArticlesByQuery(queryKey)
        insertArticles(articles)
    }

    @Query("SELECT COUNT(*) FROM articles WHERE cached_at > :timeThreshold")
    suspend fun getFreshArticleCount(timeThreshold: Long): Int
}
