package com.example.news.domain.usecase

import com.example.souhoolatask.data.remote.configuration.RemoteDataConfig.DEFAULT_PAGE_SIZE
import com.example.souhoolatask.data.remote.configuration.RemoteDataConfig.MAX_PAGE_SIZE
import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.DataValidationException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.model.ArticlesPage
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.repository.NewsRepository
import com.example.souhoolatask.utils.mapResult
import javax.inject.Inject

/**
 * Use case for fetching top headlines with business logic
 */
class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        query: String? = null,
        sortBy: SortBy,
        category: NewsCategory? = null,
        country: Country? = null
    ): Result<ArticlesPage> {
        return try {
            // Validate input parameters
            validatePaginationParams(page, pageSize)

            // Use preferences if not explicitly provided
            val effectiveSortBy = sortBy
            val effectiveCategory = category
            val effectiveCountry = country

            // Fetch articles using the traditional method
            val result = newsRepository.getTopHeadlines(
                page = page,
                pageSize = pageSize,
                query = query,
                sortBy = effectiveSortBy,
                category = effectiveCategory,
                country = effectiveCountry
            )

            // Apply business rules
            result.mapResult { articlesPage ->
                articlesPage.copy(articles = articlesPage.articles.distinctBy { it.id } // Remove duplicates
                    .sortedByDescending { it.publishedAt.toEpochMillis() } // Ensure latest first
                )
            }
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    private fun validatePaginationParams(page: Int, pageSize: Int) {
        require(page > 0) { "Page number must be positive" }
        require(pageSize > 0) { "Page size must be positive" }
        require(pageSize <= MAX_PAGE_SIZE) { "Page size too large" }
    }

    private fun mapException(exception: Exception): NewsDomainException {
        return when (exception) {
            is NewsDomainException -> exception
            is IllegalArgumentException -> DataValidationException(
                exception.message ?: "Invalid parameters"
            )

            else -> ApiUnknownException("Unknown error occurred", exception)
        }
    }
}
