package com.example.news.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

/**
 * RemoteKeyEntity - Stores pagination state for each query
 * This is the "bookmark" that remembers which page comes before/after each article
 */
@Entity(
    tableName = "remote_keys",
    primaryKeys = ["query_key", "article_id"],
    indices = [
        Index(value = ["created_at"]),
        Index(value = ["query_key"])
    ]
)
data class RemoteKeyEntity(
    @ColumnInfo(name = "query_key") val queryKey: String,
    @ColumnInfo(name = "article_id") val articleId: String,
    @ColumnInfo(name = "prev_key") val prevKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
