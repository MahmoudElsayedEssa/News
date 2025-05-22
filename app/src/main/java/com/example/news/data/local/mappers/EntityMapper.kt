package com.example.souhoolatask.data.local.mappers

import com.example.souhoolatask.data.local.entity.ArticleEntity
import com.example.souhoolatask.data.remote.dtos.ArticleDto
import com.example.news.domain.exceptions.DataParsingException
import com.example.news.domain.model.Article
import com.example.news.domain.model.Source

/**
 * Additional mapper methods for entities
 */
object EntityMapper {

    fun mapEntityToDomain(entity: ArticleEntity): Result<Article> {
        return try {
            val source = Source.create(
                id = entity.sourceId,
                name = entity.sourceName
            ).getOrThrow()

            Article.create(
                id = entity.url,
                title = entity.title,
                description = entity.description,
                content = entity.content,
                url = entity.url,
                imageUrl = entity.imageUrl,
                publishedAt = entity.publishedAt,
                source = source,
                author = entity.author
            )
        } catch (e: Exception) {
            Result.failure(DataParsingException("Failed to map entity to domain", e))
        }
    }

    fun mapDtoToEntity(
        dto: ArticleDto,
        queryKey: String? = null
    ): ArticleEntity {
        return ArticleEntity(
            url = dto.url,
            title = dto.title,
            description = dto.description,
            content = dto.content,
            imageUrl = dto.urlToImage,
            publishedAt = dto.publishedAt,
            sourceId = dto.source.id,
            sourceName = dto.source.name,
            author = dto.author,
            queryKey = queryKey
        )
    }
}
