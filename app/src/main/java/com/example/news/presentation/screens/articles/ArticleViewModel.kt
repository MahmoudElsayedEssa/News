package com.example.news.presentation.screens.articles

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.domain.events.EventDispatcher
import com.example.news.domain.events.NewsDomainEvent
import com.example.news.presentation.navigation.ArticleListNavigationEvent
import com.example.news.domain.model.Article
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.usecase.GetNewsSourcesUseCase
import com.example.news.domain.usecase.GetTopHeadlinesPagingUseCase
import com.example.news.domain.usecase.SearchArticlesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getTopHeadlinesPagingUseCase: GetTopHeadlinesPagingUseCase,
    private val searchArticlesPagingUseCase: SearchArticlesPagingUseCase,
    private val getNewsSourcesUseCase: GetNewsSourcesUseCase,
    private val eventDispatcher: EventDispatcher,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val SEARCH_DELAY_MS = 500L
        private const val MAX_SEARCH_HISTORY = 10
        private const val MIN_SEARCH_LENGTH = 2
        private const val MAX_SEARCH_LENGTH = 100
    }

    private val _stateFlow = MutableStateFlow(
        ArticleListState(
            searchQuery = savedStateHandle.get<String>("search_query") ?: "",
            selectedCategory = savedStateHandle.get<String>("selected_category")
                ?.let { NewsCategory.fromApiValue(it) },
            selectedCountry = savedStateHandle.get<String>("selected_country")
                ?.let { Country.fromCode(it) },
            selectedSortBy = savedStateHandle.get<String>("selected_sort_by")
                ?.let { SortBy.fromApiValue(it) } ?: SortBy.PUBLISHED_AT
        )
    )
    val stateFlow: StateFlow<ArticleListState> = _stateFlow.asStateFlow()

    private val _navigationEvents = Channel<ArticleListNavigationEvent>(Channel.BUFFERED)
    val navigationEvents = _navigationEvents.receiveAsFlow()

    private val searchQueryFlow = MutableStateFlow(_stateFlow.value.searchQuery)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val articles: Flow<PagingData<Article>> = combine(
        searchQueryFlow.debounce(SEARCH_DELAY_MS),
        stateFlow.map { it.selectedCategory }.distinctUntilChanged(),
        stateFlow.map { it.selectedCountry }.distinctUntilChanged(),
        stateFlow.map { it.selectedSortBy }.distinctUntilChanged()
    ) { query, category, country, sortBy ->
        QueryParams(query.trim(), category, country, sortBy)
    }.distinctUntilChanged()
        .flatMapLatest { params ->
            if (params.query.isBlank()) {
                getTopHeadlinesPagingUseCase(
                    category = params.category,
                    country = params.country,
                    sortBy = params.sortBy
                )
            } else {
                searchArticlesPagingUseCase(
                    query = params.query,
                    sortBy = params.sortBy
                )
            }
        }
        .cachedIn(viewModelScope)

    init {
        loadSources()
        observeEvents()
        monitorNetworkStatus()
    }

    private data class QueryParams(
        val query: String,
        val category: NewsCategory?,
        val country: Country?,
        val sortBy: SortBy
    )

    private fun observeEvents() {
        viewModelScope.launch {
            eventDispatcher.events.collect { event ->
                handleDomainEvent(event)
            }
        }
    }

    private fun monitorNetworkStatus() {
        viewModelScope.launch {
            _stateFlow.update {
                it.copy(networkStatus = NetworkStatus.Connected)
            }
        }
    }

    private fun handleDomainEvent(event: NewsDomainEvent) {
        when (event) {
            is NewsDomainEvent.NetworkError -> {
                _stateFlow.update {
                    it.copy(
                        error = ErrorState.NetworkError,
                        isRefreshing = false,
                        isInitialLoading = false
                    )
                }
            }
            is NewsDomainEvent.ArticlesRefreshed -> {
                _stateFlow.update {
                    it.copy(
                        isRefreshing = false,
                        isInitialLoading = false,
                        lastRefreshTime = System.currentTimeMillis(),
                        error = null
                    )
                }
            }
            is NewsDomainEvent.SearchPerformed -> {
                addToSearchHistory(event.query)
            }
            else -> {
                // Handle other events as needed
            }
        }
    }

    fun updateSearchQuery(query: String) {
        if (query.length <= MAX_SEARCH_LENGTH) {
            val sanitizedQuery = query.trim()
            _stateFlow.update { it.copy(searchQuery = sanitizedQuery) }
            searchQueryFlow.value = sanitizedQuery
            savedStateHandle["search_query"] = sanitizedQuery
        }
    }

    fun submitSearch() {
        val query = _stateFlow.value.searchQuery.trim()

        if (validateSearchQuery(query)) {
            setSearchActive(false)

            viewModelScope.launch {
                eventDispatcher.dispatch(
                    NewsDomainEvent.SearchPerformed(query, 0)
                )
            }
        }
    }

    fun setSearchActive(isActive: Boolean) {
        _stateFlow.update { it.copy(isSearchActive = isActive) }

        if (!isActive) {
            _stateFlow.update { state ->
                state.copy(
                    error = if (state.error is ErrorState.ValidationError) null else state.error
                )
            }
        }
    }

    fun selectCategory(category: NewsCategory?) {
        _stateFlow.update { it.copy(selectedCategory = category, showFilters = false) }
        savedStateHandle["selected_category"] = category?.apiValue
    }

    fun selectCountry(country: Country?) {
        _stateFlow.update { it.copy(selectedCountry = country, showFilters = false) }
        savedStateHandle["selected_country"] = country?.code
    }

    fun selectSortBy(sortBy: SortBy) {
        _stateFlow.update { it.copy(selectedSortBy = sortBy, showFilters = false) }
        savedStateHandle["selected_sort_by"] = sortBy.apiValue
    }

    fun onArticleClick(article: Article) {
        viewModelScope.launch {
            try {
                _navigationEvents.send(
                    ArticleListNavigationEvent.NavigateToDetail(article.url.value)
                )

                eventDispatcher.dispatch(
                    NewsDomainEvent.ArticleRead(article.id, 0L)
                )

            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun refresh() {
        if (!_stateFlow.value.canRefresh) {
            return
        }

        _stateFlow.update { it.copy(isRefreshing = true, error = null) }

        viewModelScope.launch {
            try {
                delay(1000)
                _stateFlow.update {
                    it.copy(
                        isRefreshing = false,
                        lastRefreshTime = System.currentTimeMillis()
                    )
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun retry() {
        _stateFlow.update { it.copy(error = null, isInitialLoading = true) }

        viewModelScope.launch {
            delay(500)
            _stateFlow.update { it.copy(isInitialLoading = false) }
        }
    }

    fun setShowFilters(show: Boolean) {
        _stateFlow.update { it.copy(showFilters = show) }
    }

    fun clearError() {
        _stateFlow.update { it.copy(error = null) }
    }

    fun clearFilters() {
        _stateFlow.update {
            it.copy(
                selectedCategory = null,
                selectedCountry = null,
                selectedSortBy = SortBy.PUBLISHED_AT,
                showFilters = false
            )
        }

        savedStateHandle.remove<String>("selected_category")
        savedStateHandle.remove<String>("selected_country")
        savedStateHandle["selected_sort_by"] = SortBy.PUBLISHED_AT.apiValue
    }

    fun onSearchHistoryItemClick(query: String) {
        updateSearchQuery(query)
        submitSearch()
    }

    fun clearSearchHistory() {
        _stateFlow.update { it.copy(searchHistory = emptyList()) }
    }

    fun bookmarkArticle(article: Article) {
        viewModelScope.launch {
            try {
                eventDispatcher.dispatch(
                    NewsDomainEvent.ArticleBookmarked(article.id)
                )
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun shareArticle(article: Article) {
        viewModelScope.launch {
            try {
                // In a real app, you'd trigger share intent here
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun validateSearchQuery(query: String): Boolean {
        return when {
            query.isBlank() -> {
                _stateFlow.update {
                    it.copy(error = ErrorState.ValidationError("search", "cannot be empty"))
                }
                false
            }
            query.length < MIN_SEARCH_LENGTH -> {
                _stateFlow.update {
                    it.copy(error = ErrorState.ValidationError("search", "must be at least $MIN_SEARCH_LENGTH characters"))
                }
                false
            }
            query.length > MAX_SEARCH_LENGTH -> {
                _stateFlow.update {
                    it.copy(error = ErrorState.ValidationError("search", "cannot exceed $MAX_SEARCH_LENGTH characters"))
                }
                false
            }
            else -> true
        }
    }

    private fun addToSearchHistory(query: String) {
        if (query.isNotBlank() && query.length >= MIN_SEARCH_LENGTH) {
            _stateFlow.update { state ->
                val newHistory = listOf(query) + state.searchHistory.filter { it != query }
                state.copy(searchHistory = newHistory.take(MAX_SEARCH_HISTORY))
            }
        }
    }

    private fun handleError(exception: Throwable) {
        val errorState = when (exception) {
            is java.net.UnknownHostException -> ErrorState.NetworkError
            is java.net.SocketTimeoutException -> ErrorState.NetworkError
            is retrofit2.HttpException -> when (exception.code()) {
                429 -> ErrorState.RateLimitError
                in 500..599 -> ErrorState.ServerError
                else -> ErrorState.UnknownError(exception.message ?: "HTTP ${exception.code()}")
            }
            else -> ErrorState.UnknownError(exception.message ?: "Unknown error")
        }

        _stateFlow.update {
            it.copy(
                error = errorState,
                isRefreshing = false,
                isInitialLoading = false
            )
        }
    }

    private fun loadSources() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoadingSources = true) }

            try {
                getNewsSourcesUseCase().fold(
                    onSuccess = { sources ->
                        _stateFlow.update {
                            it.copy(
                                availableSources = sources,
                                isLoadingSources = false
                            )
                        }
                    },
                    onFailure = { error ->
                        _stateFlow.update {
                            it.copy(
                                isLoadingSources = false,
                                error = ErrorState.UnknownError(error.message ?: "Failed to load sources")
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                _stateFlow.update {
                    it.copy(
                        isLoadingSources = false,
                        error = ErrorState.UnknownError(e.message ?: "Failed to load sources")
                    )
                }
            }
        }
    }
}