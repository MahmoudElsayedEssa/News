package com.example.souhoolatask.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.souhoolatask.data.local.database.NewsDatabase
import com.example.souhoolatask.data.local.mappers.EntityMapper
import com.example.souhoolatask.data.remote.mappers.NewsDataMapper
import com.example.souhoolatask.data.remote.paging.NewsPagingFactory
import com.example.souhoolatask.data.repository.datasources.NewsLocalDataSource
import com.example.souhoolatask.data.repository.datasources.NewsRemoteDataSource
import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.DataValidationException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.model.Article
import com.example.news.domain.model.ArticlesPage
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.model.values.SourceId
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of NewsRepository using Clean Architecture principles
 * Coordinates between remote and local data sources with offline-first approach
 */
@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource,
    private val pagingFactory: NewsPagingFactory,
    private val database: NewsDatabase
) : NewsRepository {

    override suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int,
        query: String?,
        sortBy: SortBy,
        category: NewsCategory?,
        country: Country?
    ): Result<ArticlesPage> {
        return try {
            val response = remoteDataSource.getTopHeadlines(
                query = query,
                category = category?.apiValue,
                country = country?.code,
                page = page,
                pageSize = pageSize
            )

            response.fold(onSuccess = { newsResponseDto ->
                NewsDataMapper.mapToArticlesPage(newsResponseDto, page, pageSize)
            }, onFailure = { error ->
                Result.failure(error)
            })
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    override suspend fun searchEverything(
        query: String,
        page: Int,
        pageSize: Int,
        sortBy: SortBy,
        sources: List<SourceId>?,
        fromDate: String?,
        toDate: String?
    ): Result<ArticlesPage> {
        return try {
            val response = remoteDataSource.searchEverything(
                query = query,
                sortBy = sortBy.apiValue,
                sources = sources?.map { it.value },
                from = fromDate,
                to = toDate,
                page = page,
                pageSize = pageSize
            )

            response.fold(onSuccess = { newsResponseDto ->
                NewsDataMapper.mapToArticlesPage(newsResponseDto, page, pageSize)
            }, onFailure = { error ->
                Result.failure(error)
            })
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    override suspend fun getSources(
        category: NewsCategory?, country: Country?
    ): Result<List<Source>> {
        return try {
            val response = remoteDataSource.getSources(
                category = category?.apiValue, country = country?.code
            )

            response.fold(onSuccess = { sourcesResponseDto ->
                NewsDataMapper.mapToSources(sourcesResponseDto)
            }, onFailure = { error ->
                Result.failure(error)
            })
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }


    override fun getTopHeadlinesPaging(
        category: NewsCategory?, country: Country?, query: String?, sortBy: SortBy, pageSize: Int
    ): Flow<PagingData<Article>> {
        return pagingFactory.createTopHeadlinesPager(
            category = category?.apiValue, country = country?.code, pageSize = pageSize
        ).map { pagingData ->
            pagingData.map { entity ->
                EntityMapper.mapEntityToDomain(entity).getOrThrow()
            }
        }.catch { error ->
            emit(PagingData.empty())
        }
    }

    override fun searchArticlesPaging(
        query: String,
        sortBy: SortBy,
        sources: List<SourceId>?,
        fromDate: String?,
        toDate: String?,
        pageSize: Int
    ): Flow<PagingData<Article>> {
        return pagingFactory.createSearchPager(
            query = query, sortBy = sortBy.apiValue, pageSize = pageSize
        ).map { pagingData ->
            pagingData.map { entity ->
                EntityMapper.mapEntityToDomain(entity).getOrThrow()
            }
        }.catch { error ->
            emit(PagingData.empty())
        }
    }


    override suspend fun refresh() {
        try {
            localDataSource.clearExpiredCache()
        } catch (e: Exception) {
            // Log but don't throw
        }
    }

    override suspend fun clearCache() {
        try {
            database.clearAllTables()
        } catch (e: Exception) {
            // Log but don't throw
        }
    }

    private fun mapException(exception: Exception): NewsDomainException {
        return when (exception) {
            is NewsDomainException -> exception
            is IllegalArgumentException -> DataValidationException(
                exception.message ?: "Invalid parameters"
            )

            else -> ApiUnknownException("Repository operation failed", exception)
        }
    }
}
