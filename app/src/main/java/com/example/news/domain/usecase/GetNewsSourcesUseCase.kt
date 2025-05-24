package com.example.news.domain.usecase

import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.repository.NewsRepository
import com.example.news.utils.mapResult
import javax.inject.Inject

/**
 * Use case for getting available news sources
 */
class GetNewsSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        category: NewsCategory? = null,
        country: Country? = null
    ): Result<List<Source>> {
        return try {
            newsRepository.getSources(category, country)
                .mapResult { sources ->
                    sources
                        .distinctBy { it.name.value }
                        .sortedBy { it.name.value }
                }
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    private fun mapException(exception: Exception): NewsDomainException {
        return when (exception) {
            is NewsDomainException -> exception
            else -> ApiUnknownException("Failed to fetch sources", exception)
        }
    }
}
