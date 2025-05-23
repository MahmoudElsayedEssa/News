package com.example.news.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles",
    indices = [
        Index(value = ["query_key"]),
        Index(value = ["published_at"]),
        Index(value = ["query_key", "published_at"])
    ]
)
data class ArticleEntity(
    @PrimaryKey val id: String,
    val url: String,
    val title: String,
    val description: String?,
    val content: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "source_id") val sourceId: String?,
    @ColumnInfo(name = "source_name") val sourceName: String,
    val author: String?,
    @ColumnInfo(name = "query_key") val queryKey: String,
    @ColumnInfo(name = "cached_at") val cachedAt: Long = System.currentTimeMillis()
)
