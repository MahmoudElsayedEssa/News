package com.example.news.presentation.screens.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.presentation.navigation.ArticleListNavigationEvent
import com.example.news.domain.model.Article
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.usecase.GetNewsSourcesUseCase
import com.example.news.domain.usecase.GetTopHeadlinesPagingUseCase
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import com.example.news.domain.usecase.SearchArticlesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for ArticleList Screen
 */
@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getTopHeadlinesPagingUseCase: GetTopHeadlinesPagingUseCase,
    private val searchArticlesPagingUseCase: SearchArticlesPagingUseCase,
    private val getNewsSourcesUseCase: GetNewsSourcesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(ArticleListState())
    val stateFlow: StateFlow<ArticleListState> = _stateFlow.asStateFlow()

    private val _navigationEvents = Channel<ArticleListNavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    private var currentSearchQuery = ""

    @OptIn(ExperimentalCoroutinesApi::class)
    val articles: Flow<PagingData<Article>> = stateFlow.map { state ->
            Triple(state.searchQuery, state.selectedCategory, state.selectedSortBy)
        }.distinctUntilChanged().flatMapLatest { (query, category, sortBy) ->
            if (query.isBlank()) {
                getTopHeadlinesPagingUseCase(
                    category = category, sortBy = sortBy
                )
            } else {
                searchArticlesPagingUseCase(
                    query = query, sortBy = sortBy
                )
            }
        }.cachedIn(viewModelScope)

    init {
        loadSources()
    }

    fun updateSearchQuery(query: String) {
        _stateFlow.update { it.copy(searchQuery = query) }
    }

    fun submitSearch() {
        currentSearchQuery = _stateFlow.value.searchQuery
        setSearchActive(false)
    }

    fun setSearchActive(isActive: Boolean) {
        _stateFlow.update { it.copy(isSearchActive = isActive) }
    }

    fun selectCategory(category: NewsCategory?) {
        _stateFlow.update { it.copy(selectedCategory = category, showFilters = false) }
    }

    fun selectCountry(country: Country?) {
        _stateFlow.update { it.copy(selectedCountry = country, showFilters = false) }
    }

    fun selectSortBy(sortBy: SortBy) {
        _stateFlow.update { it.copy(selectedSortBy = sortBy, showFilters = false) }
    }

    fun onArticleClick(article: Article) {
        viewModelScope.launch {
            _navigationEvents.send(
                ArticleListNavigationEvent.NavigateToDetail(article.url.value)
            )
        }
    }

    fun refresh() {
        _stateFlow.update { it.copy(isRefreshing = true) }
        // Paging 3 handles refresh automatically
        viewModelScope.launch {
            delay(1000) // Simulate refresh time
            _stateFlow.update { it.copy(isRefreshing = false) }
        }
    }

    fun retry() {
        // Handled by Paging 3
    }

    fun setShowFilters(show: Boolean) {
        _stateFlow.update { it.copy(showFilters = show) }
    }

    fun clearError() {
        _stateFlow.update { it.copy(error = null) }
    }

    private fun loadSources() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoadingSources = true) }

            getNewsSourcesUseCase().fold(onSuccess = { sources ->
                _stateFlow.update {
                    it.copy(
                        availableSources = sources, isLoadingSources = false
                    )
                }
            }, onFailure = { error ->
                _stateFlow.update {
                    it.copy(
                        isLoadingSources = false, error = error.message
                    )
                }
            })
        }
    }
}
