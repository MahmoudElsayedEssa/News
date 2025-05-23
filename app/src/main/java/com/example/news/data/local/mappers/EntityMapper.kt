package com.example.news.data.local.mappers
import com.example.news.data.local.entity.ArticleEntity
import com.example.news.data.remote.dtos.ArticleDto
import com.example.news.domain.exceptions.DataParsingException
import com.example.news.domain.model.Article
import com.example.news.domain.model.Source
import java.util.UUID.randomUUID

object EntityMapper {

    fun mapEntityToDomain(entity: ArticleEntity): Result<Article> {
        return try {
            // ✅ Handle source creation safely
            val source = Source.create(
                id = entity.sourceId,
                name = entity.sourceName
            ).getOrElse {
                // Fallback to safe default instead of crashing
                Source.create(null, "Unknown Source").getOrThrow()
            }

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
        queryKey: String
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
            queryKey = queryKey,
            id = randomUUID().toString(),
        )
    }
}