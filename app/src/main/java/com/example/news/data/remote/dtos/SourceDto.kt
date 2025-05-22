package com.example.souhoolatask.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Source DTO matching NewsAPI source object structure
 */
@Serializable
data class SourceDto(
    @SerialName("id")
    val id: String?,

    @SerialName("name")
    val name: String
)
