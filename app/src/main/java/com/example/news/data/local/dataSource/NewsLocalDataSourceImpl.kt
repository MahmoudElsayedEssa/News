package com.example.souhoolatask.data.local.dataSource

import androidx.paging.PagingSource
import com.example.souhoolatask.data.local.database.NewsDatabase
import com.example.souhoolatask.data.local.entity.ArticleEntity
import com.example.souhoolatask.data.repository.datasources.NewsLocalDataSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of local data source
 */
@Singleton
class NewsLocalDataSourceImpl @Inject constructor(
    private val database: NewsDatabase
) : NewsLocalDataSource {

    private val articlesDao = database.articlesDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override fun getArticlesPagingSource(queryKey: String): PagingSource<Int, ArticleEntity> {
        return articlesDao.getArticlesPagingSource(queryKey)
    }

    override suspend fun getArticlesByQuery(queryKey: String): List<ArticleEntity> {
        return articlesDao.getArticlesByQuery(queryKey)
    }

    override suspend fun cacheArticles(queryKey: String, articles: List<ArticleEntity>) {
        articlesDao.insertArticles(articles.map { it.copy(queryKey = queryKey) })
    }

    override suspend fun refreshArticles(queryKey: String, articles: List<ArticleEntity>) {
        articlesDao.refreshArticles(queryKey, articles.map { it.copy(queryKey = queryKey) })
    }

    override suspend fun getArticleByUrl(url: String): ArticleEntity? {
        return articlesDao.getArticleByUrl(url)
    }

    override suspend fun clearExpiredCache() {
        val expireTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)
        articlesDao.deleteExpiredArticles(expireTime)
        remoteKeysDao.deleteExpiredKeys(expireTime)
    }

    override suspend fun getCacheSize(queryKey: String): Int {
        return articlesDao.getArticleCount(queryKey)
    }

    // Additional useful methods
    suspend fun getAllCachedArticles(): List<ArticleEntity> {
        return articlesDao.getAllCachedArticles()
    }

    suspend fun clearAllCache() {
        articlesDao.deleteAllArticles()
        database.clearAllTables()
    }
}
