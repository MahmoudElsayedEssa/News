package com.example.news.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.data.local.entity.RemoteKeyEntity

/**
 * DAO for remote keys (pagination state)
 */
@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE query_key = :queryKey AND article_id = :articleId LIMIT 1")
    suspend fun getRemoteKey(queryKey: String, articleId: String): RemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(keys: List<RemoteKeyEntity>)

    @Query("DELETE FROM remote_keys WHERE query_key = :queryKey")
    suspend fun deleteRemoteKeysByQuery(queryKey: String)

    @Query("DELETE FROM remote_keys WHERE created_at < :expireTime")
    suspend fun deleteExpiredKeys(expireTime: Long)

    @Query("SELECT MAX(created_at) FROM remote_keys WHERE query_key = :queryKey")
    suspend fun getLastCacheTime(queryKey: String): Long?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllKeys()
}
