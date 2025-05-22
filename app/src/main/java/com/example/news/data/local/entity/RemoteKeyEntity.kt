package com.example.souhoolatask.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * RemoteKeyEntity - Stores pagination state for each query
 * This is the "bookmark" that remembers which page comes before/after each article
 */
@Entity(
    tableName = "remote_keys",
    primaryKeys = ["query_key", "article_url"]
)
data class RemoteKeyEntity(
    @ColumnInfo(name = "query_key") val queryKey: String,
    @ColumnInfo(name = "article_url") val articleUrl: String,
    @ColumnInfo(name = "prev_key") val prevKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
