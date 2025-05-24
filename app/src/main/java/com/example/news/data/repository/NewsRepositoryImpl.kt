package com.example.news.data.repository

import androidx.paging.PagingData
import com.example.news.data.remote.mappers.NewsDataMapper
import com.example.news.data.remote.paging.NewsPagingFactory
import com.example.news.domain.exceptions.ApiUnknownException
import com.example.news.domain.exceptions.DataNotFoundException
import com.example.news.domain.exceptions.NewsDomainException
import com.example.news.domain.model.Article
import com.example.news.domain.model.Source
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.domain.repository.NewsRepository
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
        pageSize: Int
    ): Flow<PagingData<Article>> {
        val safeCategory = category?.apiValue
        val safeCountry = country?.code ?: "us" // Default to US if no country specified

        return pagingFactory.createTopHeadlinesPager(
            category = safeCategory,
            country = safeCountry,
            query = query,
            pageSize = pageSize
        )
    }

    override fun searchArticlesPaging(
        query: String,
        sortBy: SortBy,
        pageSize: Int
    ): Flow<PagingData<Article>> {
        return pagingFactory.createSearchPager(
            query = query,
            sortBy = sortBy.apiValue,
            pageSize = pageSize
        )
    }

    override suspend fun getArticleByUrl(url: String): Result<Article> {
        return try {
            val searchQuery = extractSearchTermsFromUrl(url)
            if (searchQuery.isBlank()) {
                return Result.failure(DataNotFoundException("Cannot extract search terms from URL"))
            }

            val searchResult = remoteDataSource.searchEverything(
                query = searchQuery,
                pageSize = 10,
                page = 1
            )

            searchResult.fold(
                onSuccess = { response ->
                    val article = response.articles.firstOrNull { it.url == url }
                        ?: response.articles.firstOrNull()
                        ?: return Result.failure(DataNotFoundException("Article not found"))

                    NewsDataMapper.mapToArticle(article)
                },
                onFailure = { exception ->
                    Result.failure(mapException(exception))
                }
            )
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    override suspend fun getSources(
        category: NewsCategory?,
        country: Country?
    ): Result<List<Source>> {
        return try {
            remoteDataSource.getSources(
                category = category?.apiValue,
                country = country?.code
            ).fold(
                onSuccess = { sourcesResponseDto ->
                    NewsDataMapper.mapToSources(sourcesResponseDto)
                },
                onFailure = { exception ->
                    Result.failure(mapException(exception))
                }
            )
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    override suspend fun refresh() {
        // Implementation for refresh if needed
    }

    override suspend fun clearCache() {
        // Implementation for cache clearing if needed
    }

    override suspend fun isOnline(): Boolean {
        return try {
            InetAddress.getByName("8.8.8.8").isReachable(5000)
        } catch (e: Exception) {
            false
        }
    }

    private fun extractSearchTermsFromUrl(url: String): String {
        return url
            .substringAfterLast("/")
            .replace(Regex("[^a-zA-Z0-9\\s]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
            .take(50)
    }

    private fun mapException(exception: Throwable): NewsDomainException {
        return when (exception) {
            is NewsDomainException -> exception
            else -> ApiUnknownException("Repository operation failed", exception)
        }
    }
}