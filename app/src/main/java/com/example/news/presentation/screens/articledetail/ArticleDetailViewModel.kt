package com.example.news.presentation.screens.articledetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for ArticleDetail Screen
 */
@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(ArticleDetailState())
    val stateFlow: StateFlow<ArticleDetailState> = _stateFlow.asStateFlow()

    private var currentArticle: Article? = null

    fun loadArticle(articleUrl: String) {
        _stateFlow.update { it.copy(isLoading = true, error = null) }

        // In real implementation, you might fetch from repository
        // For now, we'll simulate loading
        viewModelScope.launch {
            delay(1000) // Simulate loading
            // This would typically come from repository.getArticleByUrl(articleUrl)
            _stateFlow.update {
                it.copy(
                    isLoading = false,
                    error = "Article not found" // Simulate error for now
                )
            }
        }
    }

    fun shareArticle(context: Context) {
        currentArticle?.let { article ->
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, article.url.value)
                putExtra(Intent.EXTRA_SUBJECT, article.title.value)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Article"))
        }
    }

    fun openInBrowser(context: Context) {
        currentArticle?.let { article ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url.value))
            context.startActivity(browserIntent)
        }
    }

    fun toggleBookmark() {
        _stateFlow.update { it.copy(isBookmarked = !it.isBookmarked) }
    }

    fun retry() {
        // Retry loading logic
    }
}
