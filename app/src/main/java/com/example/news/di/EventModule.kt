package com.example.news.di
import com.example.news.domain.events.EventDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for event-related dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object EventModule {

    @Provides
    @Singleton
    fun provideEventDispatcher(): EventDispatcher = EventDispatcher()
}