package com.example.news.presentation.screens.articles.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.news.presentation.model.ArticleUi
import com.example.news.presentation.screens.articles.ArticleListActions
import com.example.news.presentation.screens.articles.ArticleListState
import com.example.news.presentation.screens.articles.components.articlecard.ArticleCard
import com.example.news.presentation.screens.articles.components.articlecard.ArticleCardPlaceholder
import com.example.news.presentation.screens.articles.components.articlecard.LoadMoreErrorCard
import com.example.news.presentation.screens.articles.components.articlecard.LoadingMoreIndicator
import com.example.news.presentation.screens.articles.components.articlecard.ScrollToTopFAB
import com.example.news.presentation.screens.components.EmptyState
import com.example.news.presentation.screens.components.LoadingState
import com.example.news.presentation.utils.mapLoadStateError
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun ArticleListContent(
    state: ArticleListState,
    actions: ArticleListActions,
    articles: LazyPagingItems<ArticleUi>,
    onScrolled: (Boolean) -> Unit
) {
    when {
        state.isInitialLoading && articles.loadState.refresh is LoadState.Loading -> {
            LoadingState(
                modifier = Modifier.fillMaxSize(),
                message = "Discovering amazing stories...",
                showProgress = true
            )
        }

        articles.loadState.refresh is LoadState.Error && articles.itemCount == 0 -> {
            val error = articles.loadState.refresh as LoadState.Error
            ErrorState(
                error = mapLoadStateError(error.error),
                onRetry = actions.onRetry,
                isNetworkError = !state.isOnline,
                modifier = Modifier.fillMaxSize()
            )
        }

        articles.itemCount == 0 -> {
            EmptyState(
                isSearchActive = state.isSearchActive,
                searchQuery = state.searchQuery,
                hasActiveFilters = state.hasActiveFilters,
                onClearFilters = actions.onClearFilters,
                modifier = Modifier.fillMaxSize()
            )
        }

        else -> {
            ArticleList(
                articles = articles,
                onArticleClick = actions.onArticleClick,
                onScrolled = onScrolled,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ArticleList(
    articles: LazyPagingItems<ArticleUi>,
    onArticleClick: (ArticleUi) -> Unit,
    onScrolled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset > 0
        }.distinctUntilChanged().collect { isScrolled ->
            onScrolled(isScrolled)
        }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = lazyListState, modifier = Modifier
                .fillMaxSize()
                .semantics {
                    contentDescription = "Articles list with ${articles.itemCount} items"
                }, contentPadding = PaddingValues(
                horizontal = 20.dp, vertical = 16.dp
            ), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                count = articles.itemCount, key = { index ->
                    val item = articles.peek(index)
                    item?.id ?: "placeholder_$index"
                }) { index ->
                val article = articles[index]

                if (article != null) {
                    ArticleCard(
                        article = article,
                        onClick = { onArticleClick(article) },
                        modifier = Modifier.animateItem()
                    )
                } else {
                    ArticleCardPlaceholder(
                        modifier = Modifier.animateItem()
                    )
                }
            }

            articles.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        // Initial loading handled in parent
                    }

                    loadState.append is LoadState.Loading -> {
                        item(key = "loading_more") {
                            LoadingMoreIndicator()
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item(key = "append_error") {
                            LoadMoreErrorCard(
                                error = (loadState.append as LoadState.Error).error,
                                onRetry = { retry() })
                        }
                    }
                }
            }

            item(key = "bottom_spacer") {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        val showScrollToTop by remember {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex > 5
            }
        }
        val scope = rememberCoroutineScope()

        AnimatedVisibility(
            visible = showScrollToTop,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            ScrollToTopFAB(
                onClick = {
                    scope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                })
        }
    }
}










