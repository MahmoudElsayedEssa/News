package com.example.souhoolatask.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity for caching articles locally
 */
@Entity(
    tableName = "articles",
    indices = [
        Index(value = ["url"], unique = true),
        Index(value = ["published_at"]),
        Index(value = ["query_key"])
    ]
)
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val content: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "source_id") val sourceId: String?,
    @ColumnInfo(name = "source_name") val sourceName: String,
    val author: String?,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "query_key") val queryKey: String? = null
)
