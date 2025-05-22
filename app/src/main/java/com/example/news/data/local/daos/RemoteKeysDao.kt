package com.example.souhoolatask.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.souhoolatask.data.local.entity.RemoteKeyEntity

/**
 * DAO for remote keys (pagination state)
 */
@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE query_key = :queryKey AND article_url = :articleUrl")
    suspend fun getRemoteKey(queryKey: String, articleUrl: String): RemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(keys: List<RemoteKeyEntity>)

    @Query("DELETE FROM remote_keys WHERE query_key = :queryKey")
    suspend fun deleteRemoteKeysByQuery(queryKey: String)

    @Query("DELETE FROM remote_keys WHERE created_at < :expireTime")
    suspend fun deleteExpiredKeys(expireTime: Long)
}
