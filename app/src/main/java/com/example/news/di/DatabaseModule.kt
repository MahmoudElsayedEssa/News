package com.example.souhoolatask.di

import android.content.Context
import androidx.room.Room
import com.example.news.data.local.daos.ArticlesDao
import com.example.news.data.local.daos.RemoteKeysDao
import com.example.news.data.local.database.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Database module
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideArticlesDao(database: NewsDatabase): ArticlesDao = database.articlesDao()

    @Provides
    fun provideRemoteKeysDao(database: NewsDatabase): RemoteKeysDao = database.remoteKeysDao()
}
