package com.example.news.di
import com.example.news.data.validation.ArticleDtoValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for validation-related dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {

    @Provides
    @Singleton
    fun provideArticleDtoValidator(): ArticleDtoValidator = ArticleDtoValidator()
}
