package com.example.news.presentation.screens.articledetail

import androidx.compose.runtime.Stable
import com.example.news.presentation.model.ArticleUi
import com.example.news.presentation.screens.articles.NetworkStatus


/**
 * UI State that represents ArticleDetailScreen
 */
@Stable
data class ArticleDetailState(
    val articleUi: ArticleUi? = null,
    val isLoading: Boolean = false,
    val isBookmarked: Boolean = false,
    val isSharing: Boolean = false,
    val error: DetailErrorState? = null,
    val readingProgress: Float = 0f,
    val estimatedReadTime: Int = 0,
    val isFullScreenImage: Boolean = false,
    val fontSize: FontSize = FontSize.Medium,
    val networkStatus: NetworkStatus = NetworkStatus.Unknown
)
/**
 * Detail screen specific errors
 */
@Stable
sealed class DetailErrorState(
    val message: String,
) {
    object ArticleNotFound : DetailErrorState(
        message = "Article not found",
    )

    data class UnknownError(val originalMessage: String) : DetailErrorState(
        message = "Something went wrong: $originalMessage",
    )
}

/**
 * Font size options
 */
enum class FontSize(val scale: Float, val displayName: String) {
    Small(0.8f, "Small"),
    Medium(1.0f, "Medium"),
    Large(1.2f, "Large"),
    ExtraLarge(1.4f, "Extra Large")
}

/**
 * Detail screen actions
 */
@Stable
data class ArticleDetailActions(
    val onBackClick: () -> Unit = {},
    val onShareClick: () -> Unit = {},
    val onReadMoreClick: () -> Unit = {},
    val onBookmarkClick: () -> Unit = {},
    val onRetry: () -> Unit = {},
    val onImageFullScreen: (Boolean) -> Unit = {},
    val onFontSizeChange: (FontSize) -> Unit = {},
    val onReadingProgressChange: (Float) -> Unit = {},
    val onNetworkStatusChange: (NetworkStatus) -> Unit = {}
)
