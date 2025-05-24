package com.example.news.presentation.screens.articles

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.news.presentation.screens.articles.components.ArticleListContent
import com.example.news.presentation.screens.articles.components.articlecard.CustomPullToRefreshIndicator
import com.example.news.presentation.screens.articles.components.filtersbottomsheet.FiltersBottomSheet
import com.example.news.presentation.screens.articles.components.newstopbar.NewsTopBar
import com.example.news.presentation.screens.components.ErrorSnackbarHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    state: ArticleListState, actions: ArticleListActions
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isScrolled by remember { mutableStateOf(false) }

    LaunchedEffect(state.networkStatus) {
        actions.onNetworkStatusChange(state.networkStatus)
    }

    Scaffold(topBar = {
        NewsTopBar(
            searchQuery = state.searchQuery,
            isSearchActive = state.isSearchActive,
            isScrolled = isScrolled,
            onSearchQueryChange = actions.onSearchQueryChange,
            onSearchSubmit = actions.onSearchSubmit,
            onSearchActiveChange = actions.onSearchActiveChange,
            searchHistory = state.searchHistory,
            onSearchHistoryItemClick = actions.onSearchHistoryItemClick,
            onClearSearchHistory = actions.onClearSearchHistory
        )
    }, containerColor = MaterialTheme.colorScheme.background, snackbarHost = {
        ErrorSnackbarHost(
            error = state.error, onRetry = actions.onRetry, onDismiss = actions.onClearError
        )
    }) { paddingValues ->

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = actions.onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pullToRefreshState,
            indicator = {
                CustomPullToRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = state.isRefreshing,
                    state = pullToRefreshState
                )
            }) {
            // Get articles from state
            state.articles?.let { articles ->
                ArticleListContent(
                    state = state,
                    actions = actions,
                    articles = articles, // Now retrieved from state
                    onScrolled = { isScrolled = it })
            }
        }
    }

    if (state.showFilters) {
        FiltersBottomSheet(
            selectedCategory = state.selectedCategory,
            selectedCountry = state.selectedCountry,
            selectedSortBy = state.selectedSortBy,
            onCategorySelected = actions.onCategorySelected,
            onCountrySelected = actions.onCountrySelected,
            onSortBySelected = actions.onSortBySelected,
            onDismiss = { actions.onShowFilters(false) },
        )
    }
}

@Preview(name = "ArticleList", showBackground = true)
@Composable
private fun ArticleListScreenPreview() {
    MaterialTheme {
        ArticleListScreen(
            state = ArticleListState(), actions = ArticleListActions()
        )
    }
}