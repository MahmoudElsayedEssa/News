package com.example.news.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Detailed source information from /sources endpoint
 */
@Serializable
data class SourceInfoDto(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String,

    @SerialName("url")
    val url: String,

    @SerialName("category")
    val category: String,

    @SerialName("language")
    val language: String,

    @SerialName("country")
    val country: String
)
