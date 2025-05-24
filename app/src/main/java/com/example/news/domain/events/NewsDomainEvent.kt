package com.example.news.domain.events

import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.values.ArticleId

sealed class NewsDomainEvent {
    data class ArticlesRefreshed(
        val category: NewsCategory?,
        val count: Int,
        val timestamp: Long = System.currentTimeMillis()
    ) : NewsDomainEvent()

    data class ArticleBookmarked(
        val articleId: ArticleId,
        val timestamp: Long = System.currentTimeMillis()
    ) : NewsDomainEvent()

    data class ArticleUnbookmarked(
        val articleId: ArticleId,
        val timestamp: Long = System.currentTimeMillis()
    ) : NewsDomainEvent()

    data class ArticleRead(
        val articleId: ArticleId,
        val readTime: Long,
        val timestamp: Long = System.currentTimeMillis()
    ) : NewsDomainEvent()

    data class SearchPerformed(
        val query: String,
        val resultCount: Int,
        val timestamp: Long = System.currentTimeMillis()
    ) : NewsDomainEvent()

    data class CacheCleared(
        val timestamp: Long = System.currentTimeMillis()
    ) : NewsDomainEvent()

    data class NetworkError(
        val error: String,
        val timestamp: Long = System.currentTimeMillis()
    ) : NewsDomainEvent()
}
