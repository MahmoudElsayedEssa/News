package com.example.news.data.validation

import com.example.news.data.remote.dtos.ArticleDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDtoValidator @Inject constructor() : DataValidator<ArticleDto> {

    override fun validate(data: ArticleDto): ValidationResult<ArticleDto> {
        val errors = mutableListOf<ValidationError>()

        // Title validation
        if (data.title.isBlank()) {
            errors.add(ValidationError("title", "Title cannot be blank", "TITLE_BLANK"))
        } else if (data.title.length > 1000) {
            errors.add(ValidationError("title", "Title too long", "TITLE_TOO_LONG"))
        }

        // URL validation
        if (data.url.isBlank()) {
            errors.add(ValidationError("url", "URL cannot be blank", "URL_BLANK"))
        } else if (!isValidUrl(data.url)) {
            errors.add(ValidationError("url", "Invalid URL format", "URL_INVALID"))
        }

        // Published date validation
        if (data.publishedAt.isBlank()) {
            errors.add(ValidationError("publishedAt", "Published date cannot be blank", "DATE_BLANK"))
        } else if (!isValidIsoDate(data.publishedAt)) {
            errors.add(ValidationError("publishedAt", "Invalid date format", "DATE_INVALID"))
        }

        // Source validation
        if (data.source.name.isBlank()) {
            errors.add(ValidationError("source.name", "Source name cannot be blank", "SOURCE_NAME_BLANK"))
        }

        // Image URL validation (if present)
        data.urlToImage?.let { imageUrl ->
            if (imageUrl.isNotBlank() && !isValidUrl(imageUrl)) {
                errors.add(ValidationError("urlToImage", "Invalid image URL format", "IMAGE_URL_INVALID"))
            }
        }

        return if (errors.isEmpty()) {
            ValidationResult.success(data)
        } else {
            ValidationResult.failure(errors)
        }
    }

    private fun isValidUrl(url: String): Boolean {
        return url.matches(Regex("^https?://[\\w.-]+(?:\\.[\\w.-]+)+[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=.]*$"))
    }

    private fun isValidIsoDate(date: String): Boolean {
        return try {
            java.time.Instant.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}