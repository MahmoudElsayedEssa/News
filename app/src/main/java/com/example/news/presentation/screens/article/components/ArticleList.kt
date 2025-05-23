package com.example.news.presentation.screens.article.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.news.domain.model.Article
import com.example.news.presentation.screens.article.components.articlecard.ArticleCard
import com.example.news.presentation.screens.components.LoadingMoreIndicator
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ArticleList(
    articles: LazyPagingItems<Article>,
    onArticleClick: (Article) -> Unit,
    onScrolled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset > 0 }
            .distinctUntilChanged()
            .collect { isScrolled ->
                onScrolled(isScrolled)
            }
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            count = articles.itemCount,
            key = articles.itemKey { it.id.value }
        ) { index ->
            val article = articles[index]
            if (article != null) {
                ArticleCard(
                    article = article,
                    onClick = { onArticleClick(article) },
                    modifier = Modifier
                        .animateItem(
                            fadeInSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                            fadeOutSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                )
            }
        }

        // Loading States
        when (articles.loadState.append) {
            is LoadState.Loading -> {
                item {
                    LoadingMoreIndicator()
                }
            }

            is LoadState.Error -> {
                item {
//                    LoadMoreErrorCard(
//                        error = (articles.loadState.append as LoadState.Error).error.message
//                            ?: "Failed to load more articles",
//                        onRetry = { articles.retry() }
//                    )
                }
            }

            else -> {}
        }
    }
}
