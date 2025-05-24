package com.example.news.data.repository

import android.util.Log
import androidx.paging.PagingData
import com.example.news.data.remote.mappers.NewsDataMapper
import com.example.news.data.remote.paging.NewsPagingFactory
import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.model.Article
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.model.values.SourceId
import com.example.news.domain.repository.NewsRepository
import com.example.news.utils.mapResult
import kotlinx.coroutines.flow.Flow
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val pagingFactory: NewsPagingFactory
) : NewsRepository {

    override fun getTopHeadlinesPaging(
        category: NewsCategory?,
        country: Country?,
        query: String?,
        sortBy: SortBy,
        pageSize: Int
    ): Flow<PagingData<Article>> {

        val safeCategory = category?.apiValue ?: "general" //
        val safeCountry = country?.code ?: "us" //
        val safeQuery = query?.takeIf { it.isNotBlank() && it.length >= 2 } //

        return pagingFactory.createTopHeadlinesPager(
            category = safeCategory, country = safeCountry, query = safeQuery, pageSize = pageSize
        )
    }

    override fun searchArticlesPaging(
        query: String,
        sortBy: SortBy,
        sources: List<SourceId>?,
        fromDate: String?,
        toDate: String?,
        pageSize: Int
    ): Flow<PagingData<Article>> {

        val trimmedQuery = query.trim()
        if (trimmedQuery.isBlank() || trimmedQuery.length < 2) {
            throw IllegalArgumentException("Invalid search query") //
        }

        return pagingFactory.createSearchPager(
            query = trimmedQuery,
            sortBy = sortBy.apiValue,
            sources = sources?.map { it.value },
            fromDate = fromDate,
            toDate = toDate,
            pageSize = pageSize
        )
    }

    override suspend fun getSources(
        category: NewsCategory?, country: Country?
    ): Result<List<Source>> {
        return try {
            remoteDataSource.getSources( //
                category = category?.apiValue, country = country?.code
            ).mapResult { sourcesResponseDto ->
                NewsDataMapper.mapToSources(sourcesResponseDto).getOrThrow() //
            }
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    override suspend fun refresh() {
        Log.d(
            "NewsRepositoryImpl",
            "refresh() called. In network-only setup, UI should trigger PagingSource refresh."
        )
    }

    override suspend fun clearCache() {
        Log.d(
            "NewsRepositoryImpl",
            "clearCache() called. No local paging cache to clear in this setup."
        )
    }

    override suspend fun isOnline(): Boolean {
        return try {
            InetAddress.getByName("8.8.8.8").isReachable(5000) //
        } catch (e: Exception) {
            false
        }
    }

    private fun mapException(exception: Exception): NewsDomainException { //
        return when (exception) {
            is NewsDomainException -> exception
            else -> ApiUnknownException("Repository operation failed", exception)
        }
    }
}