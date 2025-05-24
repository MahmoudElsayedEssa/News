package com.example.news.presentation.screens.articledetail

import androidx.compose.runtime.Stable
import com.example.news.domain.model.Article
import com.example.news.presentation.screens.articles.NetworkStatus


/**
 * UI State that represents ArticleDetailScreen
 */
@Stable
data class ArticleDetailState(
    val article: Article? = null,
    val isLoading: Boolean = false,
    val isBookmarked: Boolean = false,
    val isSharing: Boolean = false,
    val error: DetailErrorState? = null,
    val readingProgress: Float = 0f,
    val estimatedReadTime: Int = 0,
    val isFullScreenImage: Boolean = false,
    val fontSize: FontSize = FontSize.Medium,
    val networkStatus: NetworkStatus = NetworkStatus.Unknown
) {
    val canShare: Boolean
        get() = article != null && !isSharing && networkStatus == NetworkStatus.Connected

    val canBookmark: Boolean
        get() = article != null

    val hasContent: Boolean
        get() = article?.content != null && article.content.isNotEmpty()
}

/**
 * Detail screen specific errors
 */
@Stable
sealed class DetailErrorState(
    val message: String,
    val isRetryable: Boolean = true
) {
    object ArticleNotFound : DetailErrorState(
        message = "Article not found",
        isRetryable = false
    )

    object LoadingError : DetailErrorState(
        message = "Failed to load article",
        isRetryable = true
    )

    object NetworkError : DetailErrorState(
        message = "No internet connection",
        isRetryable = true
    )

    data class UnknownError(val originalMessage: String) : DetailErrorState(
        message = "Something went wrong: $originalMessage",
        isRetryable = true
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
