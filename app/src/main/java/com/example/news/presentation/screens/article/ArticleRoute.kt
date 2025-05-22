package com.example.news.presentation.screens.article

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
    val articles = viewModel.articles.collectAsLazyPagingItems()

    // UI Actions
    val actions = rememberArticleListActions(navController, viewModel)

    // Handle navigation effects
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is ArticleListNavigationEvent.NavigateToDetail -> {
                    navController.navigate(
                        NewsDestination.ArticleDetail.createRoute(event.articleUrl)
                    )
                }
            }
        }
    }

    // UI Rendering
    ArticleListScreen(
        state = uiState,
        actions = actions,
        articles = articles
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
            onArticleClick = viewModel::onArticleClick,
            onRefresh = viewModel::refresh,
            onRetry = viewModel::retry,
            onShowFilters = viewModel::setShowFilters,
            onClearError = viewModel::clearError
        )
    }
}
