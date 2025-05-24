package com.example.news.presentation.model // Or a suitable mapper package

import com.example.news.domain.model.Article

fun Article.toArticleUi(): ArticleUi {
    return ArticleUi(
        id = this.id.value,
        title = this.title.value,
        description = this.description?.value,
        content = this.content?.value,
        url = this.url.value,
        imageUrl = this.imageUrl?.value,
        publishedAt = this.publishedAt.value,
        sourceName = this.source.name.value,
        sourceId = this.source.id?.value,
        author = this.author?.value,
        isRecent = this.isRecent(),
        readTime = this.content?.estimatedReadingTimeMinutes() ?: 1
    )
}