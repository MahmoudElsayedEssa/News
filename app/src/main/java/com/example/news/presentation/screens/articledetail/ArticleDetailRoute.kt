package com.example.news.presentation.screens.articledetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


/**
 * ArticleDetail Route
 */
@Composable
fun ArticleDetailRoute(
    navController: NavController,
    articleUrl: String,
    viewModel: ArticleDetailViewModel = hiltViewModel()
) {
    // State observing and declarations
    val uiState by viewModel.stateFlow.collectAsState()

    // Load article on composition
    LaunchedEffect(articleUrl) {
        viewModel.loadArticle(articleUrl)
    }

    // UI Actions
    val actions = rememberArticleDetailActions(navController, viewModel)

    // UI Rendering
    ArticleDetailScreen(uiState, actions)
}

@Composable
fun rememberArticleDetailActions(
    navController: NavController,
    viewModel: ArticleDetailViewModel
): ArticleDetailActions {
    val context = LocalContext.current

    return remember(navController, viewModel, context) {
        ArticleDetailActions(
            onBackClick = { navController.navigateUp() },
            onShareClick = { viewModel.shareArticle(context) },
            onReadMoreClick = { viewModel.openInBrowser(context) },
            onBookmarkClick = viewModel::toggleBookmark,
            onRetry = viewModel::retry
        )
    }
}
