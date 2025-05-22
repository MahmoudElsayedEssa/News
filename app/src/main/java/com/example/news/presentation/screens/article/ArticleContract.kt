package com.example.news.presentation.screens.article

import com.example.news.domain.model.Article
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy


/**
 * UI State that represents ArticleListScreen
 */
data class ArticleListState(
    val searchQuery: String = "",
    val selectedCategory: NewsCategory? = null,
    val selectedCountry: Country? = null,
    val selectedSortBy: SortBy = SortBy.PUBLISHED_AT,
    val isSearchActive: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val showFilters: Boolean = false,
    val availableSources: List<Source> = emptyList(),
    val isLoadingSources: Boolean = false
)

/**
 * Screen Actions emitted from the UI Layer
 * passed to the coordinator to handle
 */
data class ArticleListActions(
    val onSearchQueryChange: (String) -> Unit = {},
    val onSearchSubmit: () -> Unit = {},
    val onSearchActiveChange: (Boolean) -> Unit = {},
    val onCategorySelected: (NewsCategory?) -> Unit = {},
    val onCountrySelected: (Country?) -> Unit = {},
    val onSortBySelected: (SortBy) -> Unit = {},
    val onArticleClick: (Article) -> Unit = {},
    val onRefresh: () -> Unit = {},
    val onRetry: () -> Unit = {},
    val onShowFilters: (Boolean) -> Unit = {},
    val onClearError: () -> Unit = {}
)
