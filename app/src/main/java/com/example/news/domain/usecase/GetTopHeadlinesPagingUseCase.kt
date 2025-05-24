package com.example.news.domain.usecase

import androidx.paging.PagingData
import com.example.news.data.remote.configuration.RemoteDataConfig.DEFAULT_PAGE_SIZE
import com.example.news.domain.events.EventDispatcher
import com.example.news.domain.events.NewsDomainEvent
import com.example.news.domain.model.Article
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class GetTopHeadlinesPagingUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val eventDispatcher: EventDispatcher
) {
    operator fun invoke(
        category: NewsCategory? = NewsCategory.GENERAL,
        country: Country? = Country.US,
        sortBy: SortBy = SortBy.PUBLISHED_AT,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Article>> {

        return repository.getTopHeadlinesPaging(
            category = category,
            country = country,
            sortBy = sortBy,
            pageSize = pageSize
        )
            .onStart {
                try {
                    eventDispatcher.dispatch(
                        NewsDomainEvent.ArticlesRefreshed(category, 0)
                    )
                } catch (e: Exception) {
                    // Ignore event dispatch errors
                }
            }
            .onCompletion { exception ->
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