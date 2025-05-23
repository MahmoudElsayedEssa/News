package com.example.news.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.presentation.icons.Article
import com.example.news.presentation.icons.Business
import com.example.news.presentation.icons.Computer
import com.example.news.presentation.icons.HealthAndSafety
import com.example.news.presentation.icons.Movie
import com.example.news.presentation.icons.Schedule
import com.example.news.presentation.icons.Science
import com.example.news.presentation.icons.SportsBasketball
import com.example.news.presentation.icons.TrendingUp

fun formatRelativeTime(publishedAt: String): String {
    // Sophisticated time formatting
    return "2 hours ago" // Placeholder - implement actual logic
}

fun formatDateTime(publishedAt: String): String {
    // Implementation for date/time formatting
    return "March 15, 2024 at 2:30 PM" // Placeholder
}


@Composable
fun getCategoryIcon(category: NewsCategory): ImageVector {
    return when (category) {
        NewsCategory.BUSINESS -> Icons.Rounded.Business
        NewsCategory.ENTERTAINMENT -> Icons.Rounded.Movie
        NewsCategory.GENERAL -> Icons.Outlined.Article
        NewsCategory.HEALTH -> Icons.Rounded.HealthAndSafety
        NewsCategory.SCIENCE -> Icons.Rounded.Science
        NewsCategory.SPORTS -> Icons.Rounded.SportsBasketball
        NewsCategory.TECHNOLOGY -> Icons.Rounded.Computer
    }
}

@Composable
fun getSortIcon(sortBy: SortBy): ImageVector {
    return when (sortBy) {
        SortBy.RELEVANCY -> Icons.Rounded.TrendingUp
        SortBy.POPULARITY -> Icons.Rounded.Star
        SortBy.PUBLISHED_AT -> Icons.Outlined.Schedule
    }
}

