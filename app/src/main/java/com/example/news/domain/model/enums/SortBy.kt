package com.example.news.domain.model.enums

/**
 * Sorting options for articles
 */
enum class SortBy(val apiValue: String, val displayName: String) {
    RELEVANCY("relevancy", "Relevancy"),
    POPULARITY("popularity", "Popularity"),
    PUBLISHED_AT("publishedAt", "Latest");

    companion object {
        fun fromApiValue(value: String): SortBy? {
            return SortBy.entries.find { it.apiValue == value }
        }
    }
}
