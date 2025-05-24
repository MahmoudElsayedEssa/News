package com.example.news.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.presentation.icons.Article
import com.example.news.presentation.icons.Business
import com.example.news.presentation.icons.CloudOff
import com.example.news.presentation.icons.Computer
import com.example.news.presentation.icons.Error
import com.example.news.presentation.icons.FilterAltOff
import com.example.news.presentation.icons.HealthAndSafety
import com.example.news.presentation.icons.HourglassEmpty
import com.example.news.presentation.icons.Movie
import com.example.news.presentation.icons.Schedule
import com.example.news.presentation.icons.Science
import com.example.news.presentation.icons.SearchOff
import com.example.news.presentation.icons.SportsBasketball
import com.example.news.presentation.icons.Storage
import com.example.news.presentation.icons.TrendingUp
import com.example.news.presentation.screens.articles.ErrorState

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




 fun getErrorIcon(error: ErrorState, isNetworkError: Boolean): ImageVector {
    return when {
        isNetworkError -> CloudOff
        error is ErrorState.RateLimitError -> HourglassEmpty
        error is ErrorState.ValidationError -> Icons.Default.Warning
        error is ErrorState.ServerError -> Storage
        else -> Error
    }
}

 fun getErrorTitle(error: ErrorState, isNetworkError: Boolean): String {
    return when {
        isNetworkError -> "No Internet Connection"
        error is ErrorState.RateLimitError -> "Please Wait"
        error is ErrorState.ValidationError -> "Invalid Input"
        error is ErrorState.ServerError -> "Server Problem"
        else -> "Something Went Wrong"
    }
}

fun mapLoadStateError(throwable: Throwable): ErrorState {
    return when (throwable) {
        is java.net.UnknownHostException,
        is java.net.SocketTimeoutException -> ErrorState.NetworkError
        is retrofit2.HttpException -> when (throwable.code()) {
            429 -> ErrorState.RateLimitError
            in 500..599 -> ErrorState.ServerError
            else -> ErrorState.UnknownError(throwable.message())
        }
        else -> ErrorState.UnknownError(throwable.message ?: "Unknown error")
    }
}


 fun getEmptyStateIcon(isSearchActive: Boolean, hasActiveFilters: Boolean) = when {
    isSearchActive -> Icons.Outlined.SearchOff
    hasActiveFilters -> FilterAltOff
    else -> Icons.Outlined.Article
}

 fun getEmptyStateTitle(isSearchActive: Boolean, searchQuery: String, hasActiveFilters: Boolean) = when {
    isSearchActive && searchQuery.isNotBlank() -> "No results for \"$searchQuery\""
    isSearchActive -> "Start searching"
    hasActiveFilters -> "No articles match your filters"
    else -> "No articles available"
}

 fun getEmptyStateMessage(isSearchActive: Boolean, searchQuery: String, hasActiveFilters: Boolean) = when {
    isSearchActive && searchQuery.isNotBlank() -> "Try adjusting your search terms or browse different categories"
    isSearchActive -> "Enter a search term to find articles on topics you're interested in"
    hasActiveFilters -> "Try removing some filters to see more articles"
    else -> "Pull down to refresh or check back later for new stories"
}

fun generateSearchSuggestions(query: String): List<String> {
    // In a real app, these would come from a search API or local database
    val commonTopics = listOf(
        "technology", "science", "sports", "business", "health",
        "entertainment", "politics", "world news", "breaking news",
        "climate change", "artificial intelligence", "cryptocurrency"
    )

    return commonTopics.filter {
        it.contains(query, ignoreCase = true)
    }.take(5)
}
