package com.example.news.presentation.screens.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.domain.model.Article
import com.example.news.presentation.screens.article.components.ArticleList
import com.example.news.presentation.screens.article.components.CustomPullToRefreshIndicator
import com.example.news.presentation.screens.article.components.ErrorSnackbar
import com.example.news.presentation.screens.article.components.filtersbottomsheet.FiltersBottomSheet
import com.example.news.presentation.screens.article.components.newstopbar.NewsTopBar
import com.example.news.presentation.screens.components.EmptyState
import com.example.news.presentation.screens.components.ErrorState
import com.example.news.presentation.screens.components.LoadingState
import kotlinx.coroutines.flow.flowOf

/**
 * ArticleList Screen UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    state: ArticleListState, actions: ArticleListActions, articles: LazyPagingItems<Article>
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isScrolled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NewsTopBar(
                searchQuery = state.searchQuery,
                isSearchActive = state.isSearchActive,
                isScrolled = isScrolled,
                onSearchQueryChange = actions.onSearchQueryChange,
                onSearchSubmit = actions.onSearchSubmit,
                onSearchActiveChange = actions.onSearchActiveChange,
                onShowFilters = { actions.onShowFilters(true) })
        }, containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

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
            when {
                articles.loadState.refresh is LoadState.Loading && articles.itemCount == 0 -> {
                    LoadingState(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                articles.loadState.refresh is LoadState.Error && articles.itemCount == 0 -> {
                    ErrorState(
                        error = (articles.loadState.refresh as LoadState.Error).error.message
                            ?: "Something went wrong",
                        onRetry = actions.onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                articles.itemCount == 0 -> {
                    EmptyState(
//                        isSearchActive = state.isSearchActive,
//                        searchQuery = state.searchQuery,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    ArticleList(
                        articles = articles,
                        onArticleClick = actions.onArticleClick,
                        onScrolled = { isScrolled = it },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    // Elegant Filters Sheet
    if (state.showFilters) {
        FiltersBottomSheet(
            selectedCategory = state.selectedCategory,
            selectedCountry = state.selectedCountry,
            selectedSortBy = state.selectedSortBy,
            onCategorySelected = actions.onCategorySelected,
            onCountrySelected = actions.onCountrySelected,
            onSortBySelected = actions.onSortBySelected,
            onDismiss = { actions.onShowFilters(false) })
    }

    // Elegant Error Handling
    state.error?.let { error ->
        ErrorSnackbar(
            error = error, onDismiss = actions.onClearError
        )
    }
}


@Preview(name = "Elegant ArticleList", showBackground = true)
@Composable
private fun ArticleListScreenPreview() {
    MaterialTheme {
        ArticleListScreen(
            state = ArticleListState(),
            actions = ArticleListActions(),
            articles = flowOf(PagingData.empty<Article>()).collectAsLazyPagingItems()
        )
    }
}