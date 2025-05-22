package com.example.news.domain.model

import com.example.news.domain.model.values.SourceId
import com.example.news.domain.model.values.SourceName

/**
 * Value object representing news source
 */
data class Source(
    val id: SourceId?,
    val name: SourceName
) {
    companion object {
        fun create(id: String?, name: String): Result<Source> = runCatching {
            Source(
                id = id?.let { SourceId(it) },
                name = SourceName(name)
            )
        }
    }

    /**
     * Business rule: Source is verified if it has an ID
     */
    fun isVerified(): Boolean = id != null
}
