
package com.example.news.domain.usecase

import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        category: NewsCategory? = null,
        country: Country? = null
    ): Result<List<Source>> {
        return try {
            newsRepository.getSources(category, country)
                .fold(
                    onSuccess = { sources ->
                        Result.success(
                            sources
                                .distinctBy { it.name.value }
                                .sortedBy { it.name.value }
                        )
                    },
                    onFailure = { exception ->
                        Result.failure(mapException(exception))
                    }
                )
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    private fun mapException(exception: Throwable): NewsDomainException {
        return when (exception) {
            is NewsDomainException -> exception
            else -> ApiUnknownException("Failed to fetch sources", exception)
        }
    }
}