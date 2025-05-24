package com.example.news.presentation.screens.articledetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.news.presentation.screens.articledetail.components.ArticleDetailContent
import com.example.news.presentation.screens.articledetail.components.DetailTopBar
import com.example.news.presentation.screens.articledetail.components.FullScreenImageOverlay
import com.example.news.presentation.screens.articles.NetworkStatus
import com.example.news.presentation.screens.components.ErrorState
import com.example.news.presentation.screens.components.LoadingState
import com.example.news.presentation.screens.components.NetworkStatusBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    state: ArticleDetailState,
    actions: ArticleDetailActions
) {
    val scrollState = rememberScrollState()

    // Track reading progress based on scroll position
    LaunchedEffect(scrollState.value, scrollState.maxValue) {
        if (scrollState.maxValue > 0) {
            val progress = scrollState.value.toFloat() / scrollState.maxValue.toFloat()
            actions.onReadingProgressChange(progress)
        }
    }

    Scaffold(
        topBar = {
            DetailTopBar(
                state = state,
                actions = actions,
                scrollProgress = if (scrollState.maxValue > 0) {
                    scrollState.value.toFloat() / scrollState.maxValue.toFloat()
                } else 0f
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    LoadingState(
                        modifier = Modifier.fillMaxSize(),
                        message = "Loading article...",
                        showProgress = true
                    )
                }

                state.error != null -> {
                    ErrorState(
                        error = state.error.message,
                        onRetry = actions.onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                state.articleUi != null -> {
                    ArticleDetailContent(
                        article = state.articleUi,
                        fontSize = state.fontSize,
                        onReadMoreClick = actions.onReadMoreClick,
                        onShareClick = actions.onShareClick,
                        onImageClick = { actions.onImageFullScreen(true) },
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    )
                }
            }

            // Full-screen image overlay
            if (state.isFullScreenImage && state.articleUi?.imageUrl != null) {
                FullScreenImageOverlay(
                    imageUrl = state.articleUi.imageUrl,
                    onDismiss = { actions.onImageFullScreen(false) }
                )
            }

            // Network status indicator
            if (state.networkStatus == NetworkStatus.Disconnected) {
                NetworkStatusBanner(
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
@Preview(name = "ArticleDetail")
private fun ArticleDetailScreenPreview() {
    ArticleDetailScreen(
        state = ArticleDetailState(),
        actions = ArticleDetailActions()
    )
}