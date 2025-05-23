package com.example.news.presentation.screens.articles

import androidx.compose.runtime.Stable
import androidx.paging.compose.LazyPagingItems
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.presentation.model.ArticleUi


@Stable
data class ArticleListState(
    val searchQuery: String = "",
    val selectedCategory: NewsCategory? = null,
    val selectedCountry: Country? = null,
    val selectedSortBy: SortBy = SortBy.PUBLISHED_AT,
    val isSearchActive: Boolean = false,
    val isRefreshing: Boolean = false,
    val isInitialLoading: Boolean = true,
    val error: ErrorState? = null,
    val showFilters: Boolean = false,
    val availableSources: List<Source> = emptyList(),
    val isLoadingSources: Boolean = false,
    val networkStatus: NetworkStatus = NetworkStatus.Unknown,
    val lastRefreshTime: Long = 0L,
    val searchHistory: List<String> = emptyList(),
    val articles: LazyPagingItems<ArticleUi>? = null, // Added articles to state
    val isLoadingArticles: Boolean = false,
    val hasMoreArticles: Boolean = true
) {
    val hasActiveFilters: Boolean
        get() = selectedCategory != null || selectedCountry != null || selectedSortBy != SortBy.PUBLISHED_AT

    val isOnline: Boolean
        get() = networkStatus == NetworkStatus.Connected

    val canRefresh: Boolean
        get() = !isRefreshing && isOnline
}

@Stable
sealed class ErrorState(
    val message: String,
    val isRetryable: Boolean = true,
    val actionLabel: String? = null
) {
    object NetworkError : ErrorState(
        message = "No internet connection",
        isRetryable = true,
        actionLabel = "Retry"
    )

    object ServerError : ErrorState(
        message = "Server temporarily unavailable",
        isRetryable = true,
        actionLabel = "Try Again"
    )

    object RateLimitError : ErrorState(
        message = "Too many requests. Please wait a moment",
        isRetryable = true,
        actionLabel = "Wait"
    )

    data class ValidationError(val field: String, val reason: String) : ErrorState(
        message = "Invalid $field: $reason",
        isRetryable = false
    )

    data class UnknownError(val originalMessage: String) : ErrorState(
        message = "Something went wrong: $originalMessage",
        isRetryable = true,
        actionLabel = "Retry"
    )
}

enum class NetworkStatus {
    Connected, Disconnected, Unknown
}

@Stable
data class ArticleListActions(
    val onSearchQueryChange: (String) -> Unit = {},
    val onSearchSubmit: () -> Unit = {},
    val onSearchActiveChange: (Boolean) -> Unit = {},
    val onCategorySelected: (NewsCategory?) -> Unit = {},
    val onCountrySelected: (Country?) -> Unit = {},
    val onSortBySelected: (SortBy) -> Unit = {},
    val onArticleClick: (ArticleUi) -> Unit = {},
    val onRefresh: () -> Unit = {},
    val onRetry: () -> Unit = {},
    val onShowFilters: (Boolean) -> Unit = {},
    val onClearError: () -> Unit = {},
    val onClearFilters: () -> Unit = {},
    val onSearchHistoryItemClick: (String) -> Unit = {},
    val onClearSearchHistory: () -> Unit = {},
    val onNetworkStatusChange: (NetworkStatus) -> Unit = {},
)