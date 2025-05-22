package com.example.news.domain.model

import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.model.values.ArticleId
import com.example.news.domain.model.values.SourceId

/**
 * Data class representing user preferences
 */
data class UserPreferences(
    val sortBy: SortBy = SortBy.PUBLISHED_AT,
    val category: NewsCategory? = null,
    val country: Country? = null,
    val sources: List<SourceId> = emptyList(),
    val readArticles: Set<ArticleId> = emptySet()
)