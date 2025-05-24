package com.example.news.presentation.screens.articles

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.presentation.navigation.ArticleListNavigationEvent
import com.example.news.presentation.navigation.NewsDestination


/**
 * ArticleList Route
 */
@Composable
fun ArticleListRoute(
    navController: NavController,
    viewModel: ArticleListViewModel = hiltViewModel()
) {
    // State observing and declarations
    val uiState by viewModel.stateFlow.collectAsState()
    val articles = viewModel.getArticles().collectAsLazyPagingItems()

    // Create composite state that includes articles
    val completeState = uiState.copy(
        articles = articles,
        isLoadingArticles = articles.loadState.refresh is androidx.paging.LoadState.Loading
    )

    // UI Actions
    val actions = rememberArticleListActions(navController, viewModel)

    // Handle navigation effects
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is ArticleListNavigationEvent.NavigateToDetail -> {
                    navController.navigate(
                        NewsDestination.ArticleDetail.createRoute(event.articleJson)
                    )
                }
            }
        }
    }

    // UI Rendering - now only passes state and actions
    ArticleListScreen(
        state = completeState,
        actions = actions
    )
}


@Composable
fun rememberArticleListActions(
    navController: NavController,
    viewModel: ArticleListViewModel
): ArticleListActions {
    return remember(navController, viewModel) {
        ArticleListActions(
            onSearchQueryChange = viewModel::updateSearchQuery,
            onSearchSubmit = viewModel::submitSearch,
            onSearchActiveChange = viewModel::setSearchActive,
            onCategorySelected = viewModel::selectCategory,
            onCountrySelected = viewModel::selectCountry,
            onSortBySelected = viewModel::selectSortBy,
            onArticleClick = viewModel::onArticleClick, // Now takes ArticleUi
            onRefresh = viewModel::refresh,
            onRetry = viewModel::retry,
            onShowFilters = viewModel::setShowFilters,
            onClearError = viewModel::clearError,
            onClearFilters = viewModel::clearFilters,
            onSearchHistoryItemClick = viewModel::onSearchHistoryItemClick,
            onClearSearchHistory = viewModel::clearSearchHistory,
            onNetworkStatusChange = { status ->
            },
        )
    }
}