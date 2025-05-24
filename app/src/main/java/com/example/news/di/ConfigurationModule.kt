package com.example.news.di
import com.example.news.domain.configuration.AppConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for configuration and utility dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object ConfigurationModule {

    @Provides
    @Singleton
    fun provideAppConfiguration(): AppConfiguration = AppConfiguration()

}