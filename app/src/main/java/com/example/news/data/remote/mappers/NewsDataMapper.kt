package com.example.news.data.remote.mappers

import com.example.news.data.remote.dtos.ArticleDto
import com.example.news.data.remote.dtos.NewsResponseDto
import com.example.news.data.remote.dtos.SourceDto
import com.example.news.data.remote.dtos.SourcesResponseDto
import com.example.news.domain.exceptions.DataParsingException
import com.example.news.domain.model.Article
import com.example.news.domain.model.ArticlesPage
import com.example.news.domain.model.Source

/**
 * Mapper between API DTOs and Domain models
 * Clean separation of concerns with proper error handling
 */
object NewsDataMapper {

    /**
     * Map NewsResponseDto to ArticlesPage domain model
     */
    fun mapToArticlesPage(
        dto: NewsResponseDto, currentPage: Int, pageSize: Int
    ): Result<ArticlesPage> {
        return try {
            if (dto.status != "ok") {
                return Result.failure(
                    IllegalStateException("API returned error status: ${dto.status}, message: ${dto.message}")
                )
            }

            val articles = dto.articles.mapNotNull { articleDto ->
                mapToArticle(articleDto).getOrNull()
            }

            val articlesPage = ArticlesPage.create(
                articles = articles,
                currentPage = currentPage,
                totalResults = dto.totalResults,
                pageSize = pageSize
            )

            Result.success(articlesPage)
        } catch (e: Exception) {
            Result.failure(DataParsingException("Failed to map articles page", e))
        }
    }

    /**
     * Map ArticleDto to Article domain model
     */
    fun mapToArticle(dto: ArticleDto): Result<Article> {
        return try {
            val source = mapToSource(dto.source).getOrThrow()

            Article.create(
                id = dto.url, // Using URL as unique identifier
                title = dto.title,
                description = dto.description,
                content = dto.content,
                url = dto.url,
                imageUrl = dto.urlToImage,
                publishedAt = dto.publishedAt,
                source = source,
                author = dto.author
            )
        } catch (e: Exception) {
            Result.failure(DataParsingException("Failed to map article: ${e.message}", e))
        }
    }

    /**
     * Map SourceDto to Source domain model
     */
    fun mapToSource(dto: SourceDto): Result<Source> {
        return Source.create(
            id = dto.id, name = dto.name
        )
    }

    /**
     * Map SourcesResponseDto to List<Source>
     */
    fun mapToSources(dto: SourcesResponseDto): Result<List<Source>> {
        return try {
            if (dto.status != "ok") {
                return Result.failure(
                    IllegalStateException("API returned error status: ${dto.status}, message: ${dto.message}")
                )
            }

            val sources = dto.sources.mapNotNull { sourceDto ->
                Source.create(
                    id = sourceDto.id, name = sourceDto.name
                ).getOrNull()
            }

            Result.success(sources)
        } catch (e: Exception) {
            Result.failure(DataParsingException("Failed to map sources", e))
        }
    }
}
