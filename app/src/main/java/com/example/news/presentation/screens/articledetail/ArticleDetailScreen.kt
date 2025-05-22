package com.example.news.presentation.screens.articledetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.news.presentation.icons.Bookmark
import com.example.news.presentation.icons.BookmarkBorder
import com.example.news.presentation.screens.articledetail.components.ArticleDetailContent
import com.example.news.presentation.screens.components.ErrorState
import com.example.news.presentation.screens.components.LoadingState

/**
 * ArticleDetail Screen UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    state: ArticleDetailState, actions: ArticleDetailActions
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Article Details", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                IconButton(onClick = actions.onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }, actions = {
                IconButton(onClick = actions.onShareClick) {
                    Icon(
                        imageVector = Icons.Default.Share, contentDescription = "Share"
                    )
                }
                IconButton(onClick = actions.onBookmarkClick) {
                    Icon(
                        imageVector = if (state.isBookmarked) {
                            Icons.Filled.Bookmark
                        } else {
                            Icons.Outlined.BookmarkBorder
                        },
                        contentDescription = if (state.isBookmarked) "Remove bookmark" else "Add bookmark",
                        tint = if (state.isBookmarked) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            })
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    LoadingState(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                state.error != null -> {
                    ErrorState(
                        error = state.error,
                        onRetry = actions.onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                state.article != null -> {
                    ArticleDetailContent(
                        article = state.article,
                        onReadMoreClick = actions.onReadMoreClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = "ArticleDetail")
private fun ArticleDetailScreenPreview() {
    ArticleDetailScreen(
        state = ArticleDetailState(), actions = ArticleDetailActions()
    )
}

