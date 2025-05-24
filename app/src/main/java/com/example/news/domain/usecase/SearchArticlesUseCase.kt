package com.example.news.domain.usecase


import androidx.paging.PagingData
import com.example.news.data.remote.configuration.RemoteDataConfig.DEFAULT_PAGE_SIZE
import com.example.news.domain.events.EventDispatcher
import com.example.news.domain.events.NewsDomainEvent
import com.example.news.domain.model.Article
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.model.values.SourceId
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(
    private val repository: NewsRepository, private val eventDispatcher: EventDispatcher
) {
    operator fun invoke(
        query: String,
        sortBy: SortBy = SortBy.RELEVANCY,
        sources: List<SourceId>? = null,
        fromDate: String? = null,
        toDate: String? = null,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Article>> {

        return repository.searchArticlesPaging(
            query = query,
            sortBy = sortBy,
            sources = sources,
            fromDate = fromDate,
            toDate = toDate,
            pageSize = pageSize
        ).onStart {
                // Dispatch search start event
                eventDispatcher.tryDispatch(
                    NewsDomainEvent.SearchPerformed(query, 0)
                )
            }.catch { exception ->
                // Dispatch error event
                eventDispatcher.tryDispatch(
                    NewsDomainEvent.NetworkError(exception.message ?: "Unknown error")
                )
                emit(PagingData.empty())
            }.onCompletion { exception ->
                if (exception == null) {
                    // Success completion
                    eventDispatcher.tryDispatch(
                        NewsDomainEvent.SearchPerformed(query, -1) // -1 indicates completion
                    )
                }
            }
    }
}