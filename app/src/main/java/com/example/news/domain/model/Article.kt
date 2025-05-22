package com.example.news.domain.model

import com.example.news.domain.model.values.ArticleId
import com.example.news.domain.model.values.ArticleUrl
import com.example.news.domain.model.values.Author
import com.example.news.domain.model.values.Content
import com.example.news.domain.model.values.Description
import com.example.news.domain.model.values.ImageUrl
import com.example.news.domain.model.values.PublishedAt
import com.example.news.domain.model.values.Title

/**
 * Core domain entity representing a news article
 * Immutable data class with proper validation and business rules
 */
data class Article(
    val id: ArticleId,
    val title: Title,
    val description: Description?,
    val content: Content?,
    val url: ArticleUrl,
    val imageUrl: ImageUrl?,
    val publishedAt: PublishedAt,
    val source: Source,
    val author: Author?
) {
    companion object {
        /**
         * Factory method with validation
         */
        fun create(
            id: String,
            title: String,
            description: String?,
            content: String?,
            url: String,
            imageUrl: String?,
            publishedAt: String,
            source: Source,
            author: String?
        ): Result<Article> = runCatching {
            Article(
                id = ArticleId(id),
                title = Title(title),
                description = description?.let { Description(it) },
                content = content?.let { Content(it) },
                url = ArticleUrl(url),
                imageUrl = imageUrl?.let { ImageUrl(it) },
                publishedAt = PublishedAt(publishedAt),
                source = source,
                author = author?.let { Author(it) }
            )
        }
    }

    /**
     * Business rule: Article is recent if published within last 24 hours
     */
    fun isRecent(): Boolean = publishedAt.isWithinLast24Hours()

    /**
     * Business rule: Article has media content
     */
    fun hasImage(): Boolean = imageUrl != null

    /**
     * Business rule: Article has full content available
     */
    fun hasFullContent(): Boolean = content != null && content.isNotEmpty()
}
