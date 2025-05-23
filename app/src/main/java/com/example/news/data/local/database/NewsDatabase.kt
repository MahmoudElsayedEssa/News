package com.example.news.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.data.local.daos.ArticlesDao
import com.example.news.data.local.daos.RemoteKeysDao
import com.example.news.data.local.entity.ArticleEntity
import com.example.news.data.local.entity.RemoteKeyEntity

/**
 *  Room database
 */
@Database(
    entities = [ArticleEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
