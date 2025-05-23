package com.example.news.data.local.dataSource

import androidx.paging.PagingSource
import com.example.news.data.local.database.NewsDatabase
import com.example.news.data.local.entity.ArticleEntity
import com.example.news.data.repository.datasources.NewsLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
        return withContext(Dispatchers.IO) {
            articlesDao.getArticlesByQuery(queryKey)
        }
    }

    override suspend fun cacheArticles(queryKey: String, articles: List<ArticleEntity>) {
        withContext(Dispatchers.IO) {
            val articlesWithQueryKey = articles.map { it.copy(queryKey = queryKey) }
            articlesDao.insertArticles(articlesWithQueryKey)
        }
    }

    override suspend fun refreshArticles(queryKey: String, articles: List<ArticleEntity>) {
        withContext(Dispatchers.IO) {
            val articlesWithQueryKey = articles.map { it.copy(queryKey = queryKey) }
            articlesDao.refreshArticles(queryKey, articlesWithQueryKey)
        }
    }

    override suspend fun getArticleByUrl(url: String): ArticleEntity? {
        return withContext(Dispatchers.IO) {
            articlesDao.getArticleByUrl(url)
        }
    }

    override suspend fun clearExpiredCache() {
        withContext(Dispatchers.IO) {
            val expireTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)
            articlesDao.deleteExpiredArticles(expireTime)
            remoteKeysDao.deleteExpiredKeys(expireTime)
        }
    }

    override suspend fun getCacheSize(queryKey: String): Int {
        return withContext(Dispatchers.IO) {
            articlesDao.getArticleCount(queryKey)
        }
    }


    suspend fun clearAllCache() {
        withContext(Dispatchers.IO) {
            database.clearAllTables()
        }
    }

}
