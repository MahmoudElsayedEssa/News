package com.example.news.presentation.screens.articledetail

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.events.EventDispatcher
import com.example.news.domain.events.NewsDomainEvent
import com.example.news.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for ArticleDetail Screen
 */
@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val eventDispatcher: EventDispatcher,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val READING_PROGRESS_UPDATE_DELAY = 100L
        private const val AUTO_BOOKMARK_THRESHOLD = 0.8f // 80% read
    }

    private val _stateFlow = MutableStateFlow(
        ArticleDetailState(fontSize = FontSize.entries.find {
                it.name == savedStateHandle.get<String>(
                    "font_size"
                )
            } ?: FontSize.Medium))
    val stateFlow: StateFlow<ArticleDetailState> = _stateFlow.asStateFlow()

    private var currentArticleUrl: String? = null

    fun loadArticle(articleUrl: String) {
        if (currentArticleUrl == articleUrl && _stateFlow.value.article != null) {
            return
        }

        currentArticleUrl = articleUrl
        _stateFlow.update {
            it.copy(
                isLoading = true, error = null, article = null
            )
        }

        viewModelScope.launch {
            try {

                // Simulate loading with realistic delay
                delay(1000)

                // In a real implementation, you would:
                // val result = repository.getArticleByUrl(articleUrl)
                // For now, simulate not finding the article

                _stateFlow.update {
                    it.copy(
                        isLoading = false, error = DetailErrorState.ArticleNotFound
                    )
                }


            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun shareArticle(context: Context) {
        val article = _stateFlow.value.article
        if (article == null || _stateFlow.value.isSharing) {
            return
        }

        _stateFlow.update { it.copy(isSharing = true) }

        viewModelScope.launch {
            try {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, article.url.value)
                    putExtra(Intent.EXTRA_SUBJECT, article.title.value)
                }

                val chooserIntent = Intent.createChooser(shareIntent, "Share Article")
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(chooserIntent)


                // Dispatch share event
                eventDispatcher.dispatch(
                    NewsDomainEvent.ArticleRead(article.id, 0L) // Could track share as engagement
                )

            } catch (e: Exception) {
                _stateFlow.update {
                    it.copy(error = DetailErrorState.UnknownError("Failed to share article"))
                }
            } finally {
                _stateFlow.update { it.copy(isSharing = false) }
            }
        }
    }

    fun openInBrowser(context: Context) {
        val article = _stateFlow.value.article
        if (article == null) {
            return
        }

        viewModelScope.launch {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, article.url.value.toUri())
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(browserIntent)


            } catch (e: Exception) {
                _stateFlow.update {
                    it.copy(error = DetailErrorState.UnknownError("Failed to open browser"))
                }
            }
        }
    }

    fun toggleBookmark() {
        val article = _stateFlow.value.article
        if (article == null) {
            return
        }

        val currentBookmarkState = _stateFlow.value.isBookmarked
        _stateFlow.update { it.copy(isBookmarked = !currentBookmarkState) }

        viewModelScope.launch {
            try {
                val event = if (currentBookmarkState) {
                    NewsDomainEvent.ArticleUnbookmarked(article.id)
                } else {
                    NewsDomainEvent.ArticleBookmarked(article.id)
                }

                eventDispatcher.dispatch(event)

                val action = if (currentBookmarkState) "unbookmarked" else "bookmarked"

            } catch (e: Exception) {
                // Revert the bookmark state on error
                _stateFlow.update { it.copy(isBookmarked = currentBookmarkState) }
            }
        }
    }

    fun updateReadingProgress(progress: Float) {
        val clampedProgress = progress.coerceIn(0f, 1f)
        _stateFlow.update { it.copy(readingProgress = clampedProgress) }

        // Auto-bookmark if user has read most of the article
        if (clampedProgress >= AUTO_BOOKMARK_THRESHOLD && !_stateFlow.value.isBookmarked) {
            toggleBookmark()
        }

        // Update reading progress periodically
        viewModelScope.launch {
            delay(READING_PROGRESS_UPDATE_DELAY)
            savedStateHandle["reading_progress"] = clampedProgress
        }
    }

    fun setFullScreenImage(isFullScreen: Boolean) {
        _stateFlow.update { it.copy(isFullScreenImage = isFullScreen) }
    }

    fun changeFontSize(fontSize: FontSize) {
        _stateFlow.update { it.copy(fontSize = fontSize) }
        savedStateHandle["font_size"] = fontSize.name
    }

    fun retry() {
        val articleUrl = currentArticleUrl
        if (articleUrl != null) {
            loadArticle(articleUrl)
        }
    }

    fun clearError() {
        _stateFlow.update { it.copy(error = null) }
    }

    private fun handleError(exception: Throwable) {
        val errorState = when (exception) {
            is java.net.UnknownHostException, is java.net.SocketTimeoutException -> DetailErrorState.NetworkError

            else -> DetailErrorState.UnknownError(exception.message ?: "Unknown error")
        }

        _stateFlow.update {
            it.copy(
                error = errorState, isLoading = false
            )
        }
    }

    private fun calculateEstimatedReadTime(article: Article): Int {
        val content = (article.description?.value ?: "") + (article.content?.value ?: "")
        val wordCount = content.split("\\s+".toRegex()).size
        return maxOf(1, wordCount / 200) // Average reading speed: 200 words per minute
    }
}
