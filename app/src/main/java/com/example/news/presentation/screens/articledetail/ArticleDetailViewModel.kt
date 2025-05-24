package com.example.news.presentation.screens.articledetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.events.EventDispatcher
import com.example.news.domain.events.NewsDomainEvent
import com.example.news.presentation.model.ArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val eventDispatcher: EventDispatcher, private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val AUTO_BOOKMARK_THRESHOLD = 0.8f
        private const val READING_PROGRESS_UPDATE_DELAY = 300L
    }

    private val _stateFlow = MutableStateFlow( //
        ArticleDetailState(fontSize = FontSize.entries.find {
            it.name == savedStateHandle.get<String>("font_size")
        } ?: FontSize.Medium))
    val stateFlow: StateFlow<ArticleDetailState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val encodedJsonString: String? = savedStateHandle.get<String>("articleJson")
            if (encodedJsonString != null) {
                try {
                    val jsonString = Uri.decode(encodedJsonString)
                    val articleUiModel =
                        Json.decodeFromString<ArticleUi>(jsonString) // Deserialize to ArticleUi
                    _stateFlow.update {
                        it.copy(
                            articleUi = articleUiModel, // Set ArticleUi
                            isLoading = false,
                            error = null,
                            estimatedReadTime = articleUiModel.readTime
                        )
                    }
                    // Dispatch ArticleRead event using the ID from ArticleUi
                    eventDispatcher.dispatch(
                        NewsDomainEvent.ArticleRead(
                            com.example.news.domain.model.values.ArticleId(
                                articleUiModel.id
                            ), 0L
                        ) // Reconstruct ArticleId for event
                    )

                } catch (e: Exception) {
                    _stateFlow.update {
                        it.copy(
                            isLoading = false,
                            error = DetailErrorState.UnknownError("Failed to load article data."),
                            articleUi = null
                        )
                    }
                }
            } else {
                _stateFlow.update {
                    it.copy(
                        isLoading = false,
                        error = DetailErrorState.ArticleNotFound,
                        articleUi = null
                    )
                }
            }
        }
    }


    fun retry() {
        _stateFlow.update { it.copy(isLoading = true, error = null) }
        // Re-attempt deserialization from SavedStateHandle
        viewModelScope.launch {
            val encodedJsonString: String? = savedStateHandle.get<String>("articleJson")
            if (encodedJsonString != null) {
                try {
                    val jsonString = Uri.decode(encodedJsonString)
                    val articleUiModel = Json.decodeFromString<ArticleUi>(jsonString)
                    _stateFlow.update {
                        it.copy(
                            articleUi = articleUiModel,
                            isLoading = false,
                            error = null,
                            estimatedReadTime = articleUiModel.readTime
                        )
                    }
                } catch (e: Exception) {
                    _stateFlow.update {
                        it.copy(
                            isLoading = false,
                            error = DetailErrorState.UnknownError("Failed to reload article data."),
                            articleUi = null
                        )
                    }
                }
            } else {
                _stateFlow.update {
                    it.copy(
                        isLoading = false,
                        error = DetailErrorState.ArticleNotFound,
                        articleUi = null
                    )
                }
            }
        }
    }

    // Update shareArticle, openInBrowser, toggleBookmark etc. to use _stateFlow.value.articleUi
    fun shareArticle(context: Context) { //
        val articleUi = _stateFlow.value.articleUi // Use articleUi
        if (articleUi == null || _stateFlow.value.isSharing) return

        _stateFlow.update { it.copy(isSharing = true) } //
        viewModelScope.launch { //
            try { //
                val shareIntent = Intent().apply { //
                    action = Intent.ACTION_SEND //
                    type = "text/plain" //
                    putExtra(Intent.EXTRA_TEXT, articleUi.url) // Use articleUi.url
                    putExtra(Intent.EXTRA_SUBJECT, articleUi.title) // Use articleUi.title
                }
                val chooserIntent = Intent.createChooser(shareIntent, "Share Article") //
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //
                context.startActivity(chooserIntent) //
                eventDispatcher.dispatch(
                    NewsDomainEvent.ArticleRead(
                        com.example.news.domain.model.values.ArticleId(
                            articleUi.id
                        ), 0L
                    )
                ) //
            } catch (e: Exception) { //
                _stateFlow.update { it.copy(error = DetailErrorState.UnknownError("Failed to share article")) } //
            } finally {
                _stateFlow.update { it.copy(isSharing = false) } //
            }
        }
    }

    fun openInBrowser(context: Context) { //
        val articleUi = _stateFlow.value.articleUi // Use articleUi
        if (articleUi == null) return

        viewModelScope.launch { //
            try { //
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, articleUi.url.toUri()) // Use articleUi.url
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //
                context.startActivity(browserIntent) //
            } catch (e: Exception) { //
                _stateFlow.update { it.copy(error = DetailErrorState.UnknownError("Failed to open browser")) } //
            }
        }
    }

    fun toggleBookmark() { //
        val articleUi = _stateFlow.value.articleUi // Use articleUi
        if (articleUi == null) return

        val currentBookmarkState = _stateFlow.value.isBookmarked //
        _stateFlow.update { it.copy(isBookmarked = !currentBookmarkState) } //
        viewModelScope.launch { //
            try { //
                val event = if (currentBookmarkState) { //
                    NewsDomainEvent.ArticleUnbookmarked(
                        com.example.news.domain.model.values.ArticleId(
                            articleUi.id
                        )
                    ) //
                } else { //
                    NewsDomainEvent.ArticleBookmarked(
                        com.example.news.domain.model.values.ArticleId(
                            articleUi.id
                        )
                    ) //
                }
                eventDispatcher.dispatch(event) //
            } catch (e: Exception) { //
                _stateFlow.update { it.copy(isBookmarked = currentBookmarkState) } //
            }
        }
    }


    fun updateReadingProgress(progress: Float) {
        val clampedProgress = progress.coerceIn(0f, 1f)
        _stateFlow.update { it.copy(readingProgress = clampedProgress) }

        if (clampedProgress >= AUTO_BOOKMARK_THRESHOLD && !_stateFlow.value.isBookmarked) { //
            toggleBookmark() //
        }

        viewModelScope.launch { //
            delay(READING_PROGRESS_UPDATE_DELAY) //
            savedStateHandle["reading_progress"] = clampedProgress //
        }
    }

    fun setFullScreenImage(isFullScreen: Boolean) { //
        _stateFlow.update { it.copy(isFullScreenImage = isFullScreen) } //
    }

    fun changeFontSize(fontSize: FontSize) { //
        _stateFlow.update { it.copy(fontSize = fontSize) } //
        savedStateHandle["font_size"] = fontSize.name //
    }

}