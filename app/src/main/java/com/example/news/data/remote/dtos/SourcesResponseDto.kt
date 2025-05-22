package com.example.souhoolatask.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sources response wrapper for /sources endpoint
 */
@Serializable
data class SourcesResponseDto(
    @SerialName("status")
    val status: String,

    @SerialName("sources")
    val sources: List<SourceInfoDto>,

    @SerialName("code")
    val code: String? = null,

    @SerialName("message")
    val message: String? = null
)
