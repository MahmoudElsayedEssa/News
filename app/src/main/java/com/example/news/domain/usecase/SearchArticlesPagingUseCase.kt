package com.example.news.domain.usecase

import androidx.paging.PagingData
import com.example.souhoolatask.data.remote.configuration.RemoteDataConfig.DEFAULT_PAGE_SIZE
import com.example.news.domain.model.Article
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.model.values.SourceId
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for searching articles with advanced filtering
 */
class SearchArticlesPagingUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(
        query: String,
        sortBy: SortBy = SortBy.RELEVANCY,
        sources: List<SourceId>? = null,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Article>> {

        // Keep your robust validation
        require(query.isNotBlank()) { "Search query cannot be blank" }
        require(query.length <= 500) { "Search query too long" }

        return repository.searchArticlesPaging(
            query = query.trim(),
            sortBy = sortBy,
            sources = sources,
            pageSize = pageSize
        )
    }
}
