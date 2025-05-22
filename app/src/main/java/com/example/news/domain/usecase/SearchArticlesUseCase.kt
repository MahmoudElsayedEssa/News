package com.example.news.domain.usecase

import com.example.souhoolatask.data.remote.configuration.RemoteDataConfig.DEFAULT_PAGE_SIZE
import com.example.souhoolatask.data.remote.configuration.RemoteDataConfig.MAX_PAGE_SIZE
import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.DataValidationException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.model.ArticlesPage
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.model.values.SourceId
import com.example.news.domain.repository.NewsRepository
import com.example.souhoolatask.utils.mapResult
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        sortBy: SortBy = SortBy.RELEVANCY,
        sources: List<SourceId>? = null,
        fromDate: String? = null,
        toDate: String? = null
    ): Result<ArticlesPage> {
        return try {
            // Validate search parameters (your robust validation pattern)
            validateSearchParams(query, page, pageSize)

            // Fetch articles
            val result = newsRepository.searchEverything(
                query = query.trim(),
                page = page,
                pageSize = pageSize,
                sortBy = sortBy,
                sources = sources,
                fromDate = fromDate,
                toDate = toDate
            )

            // Apply business rules (your pattern)
            result.mapResult { articlesPage ->
                articlesPage.copy(articles = articlesPage.articles.distinctBy { it.id } // Remove duplicates
                    .filter { it.title.value.isNotBlank() } // Ensure valid titles
                )
            }
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    private fun validateSearchParams(query: String, page: Int, pageSize: Int) {
        require(query.isNotBlank()) { "Search query cannot be blank" }
        require(query.length <= 500) { "Search query too long" }
        require(page > 0) { "Page number must be positive" }
        require(pageSize > 0) { "Page size must be positive" }
        require(pageSize <= MAX_PAGE_SIZE) { "Page size too large" }
    }

    private fun mapException(exception: Exception): NewsDomainException {
        return when (exception) {
            is NewsDomainException -> exception
            is IllegalArgumentException -> DataValidationException(
                exception.message ?: "Invalid search parameters"
            )

            else -> ApiUnknownException("Search failed", exception)
        }
    }
}
