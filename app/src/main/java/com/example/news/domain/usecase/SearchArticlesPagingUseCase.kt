package com.example.news.domain.usecase

import androidx.paging.PagingData
import com.example.news.domain.events.EventDispatcher
import com.example.news.domain.events.NewsDomainEvent
import com.example.news.domain.model.Article
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchArticlesPagingUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val eventDispatcher: EventDispatcher
) {
    operator fun invoke(
        query: String,
        sortBy: SortBy = SortBy.RELEVANCY,
        pageSize: Int = 20
    ): Flow<PagingData<Article>> {

        require(query.isNotBlank()) { "Search query cannot be blank" }

        return repository.searchArticlesPaging(
            query = query.trim(),
            sortBy = sortBy,
            pageSize = pageSize
        )
            .onStart {
                try {
                    eventDispatcher.dispatch(
                        NewsDomainEvent.SearchPerformed(query.trim(), 0)
                    )
                } catch (e: Exception) {
                    // Ignore event dispatch errors
                }
            }
            .onCompletion { exception ->
                try {
                    if (exception == null) {
                        eventDispatcher.dispatch(
                            NewsDomainEvent.SearchPerformed(query.trim(), -1)
                        )
                    }
                } catch (e: Exception) {
                    // Ignore event dispatch errors
                }
            }
    }
}
