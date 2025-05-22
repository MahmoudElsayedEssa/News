package com.example.news.presentation.screens.articledetail

import com.example.news.domain.model.Article


/**
 * UI State that represents ArticleDetailScreen
 */
data class ArticleDetailState(
    val article: Article? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isBookmarked: Boolean = false
)

/**
 * Screen Actions emitted from the UI Layer
 * passed to the coordinator to handle
 */
data class ArticleDetailActions(
    val onBackClick: () -> Unit = {},
    val onShareClick: () -> Unit = {},
    val onReadMoreClick: () -> Unit = {},
    val onBookmarkClick: () -> Unit = {},
    val onRetry: () -> Unit = {}
)
