package com.example.souhoolatask.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Root response wrapper from NewsAPI
 * Matches the exact JSON structure from the API
 */
@Serializable
data class NewsResponseDto(
    @SerialName("status")
    val status: String,

    @SerialName("totalResults")
    val totalResults: Int,

    @SerialName("articles")
    val articles: List<ArticleDto>,

    @SerialName("code")
    val code: String? = null,

    @SerialName("message")
    val message: String? = null
)
