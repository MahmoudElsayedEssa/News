package com.example.news.domain.model.enums

/**
 * News categories for filtering
 */
enum class NewsCategory(val apiValue: String, val displayName: String) {
    BUSINESS("business", "Business"),
    ENTERTAINMENT("entertainment", "Entertainment"),
    GENERAL("general", "General"),
    HEALTH("health", "Health"),
    SCIENCE("science", "Science"),
    SPORTS("sports", "Sports"),
    TECHNOLOGY("technology", "Technology");

    companion object {
        fun fromApiValue(value: String): NewsCategory? {
            return NewsCategory.entries.find { it.apiValue == value }
        }
    }
}
