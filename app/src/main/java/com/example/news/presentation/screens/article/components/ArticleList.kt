//package com.example.news.presentation.screens.article.components
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.paging.LoadState
//import androidx.paging.compose.LazyPagingItems
//import androidx.paging.compose.itemKey
//import com.example.news.presentation.screens.components.ErrorItem
//import com.example.souhoolatask.domain.model.Article
//
//@Composable
//fun ArticleList(
//    articles: LazyPagingItems<Article>,
//    onArticleClick: (Article) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    LazyColumn(
//        modifier = modifier,
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        items(
//            count = articles.itemCount, key = articles.itemKey { it.id.value }) { index ->
//            val article = articles[index]
//            if (article != null) {
//                ArticleItem(
//                    article = article, onClick = { onArticleClick(article) })
//            }
//        }
//
//        // Loading more indicator
//        when (articles.loadState.append) {
//            is LoadState.Loading -> {
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator()
//                    }
//                }
//            }
//
//            is LoadState.Error -> {
//                item {
//                    ErrorItem(
//                        error = (articles.loadState.append as LoadState.Error).error.message
//                            ?: "Failed to load more", onRetry = { articles.retry() })
//                }
//            }
//
//            else -> {}
//        }
//    }
//}
