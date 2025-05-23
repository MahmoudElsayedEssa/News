package com.example.news.domain.usecase

import androidx.paging.PagingData
import com.example.news.domain.events.EventDispatcher
import com.example.news.domain.events.NewsDomainEvent
import com.example.news.domain.model.Article
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetTopHeadlinesPagingUseCase @Inject constructor(
    private val repository: NewsRepository, private val eventDispatcher: EventDispatcher
) {
    operator fun invoke(
        category: NewsCategory? = null,
        country: Country? = Country.US,
        pageSize: Int = 20
    ): Flow<PagingData<Article>> {

        return repository.getTopHeadlinesPaging(
            category = category, country = country ?: Country.US, pageSize = pageSize
        ).onStart {
                try {
                    eventDispatcher.dispatch(
                        NewsDomainEvent.ArticlesRefreshed(category, 0)
                    )
                } catch (e: Exception) {
                    // Ignore event dispatch errors to prevent crashes
                }
            }.catch { exception ->
                // Handle errors gracefully without crashing
                try {
                    eventDispatcher.dispatch(
                        NewsDomainEvent.NetworkError(
                            exception.message ?: "Failed to load headlines"
                        )
                    )
                } catch (e: Exception) {
                    // Ignore event dispatch errors
                }
                // Re-emit empty data instead of crashing
                // The UI will handle this via LoadState.Error
            }.onCompletion { exception ->
                try {
                    if (exception == null) {
                        eventDispatcher.dispatch(
                            NewsDomainEvent.ArticlesRefreshed(category, -1)
                        )
                    }
                } catch (e: Exception) {
                    // Ignore event dispatch errors
                }
            }
    }
}