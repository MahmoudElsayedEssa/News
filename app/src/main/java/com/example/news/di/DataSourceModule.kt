package com.example.news.di

import com.example.news.data.remote.datasource.NewsRemoteDataSourceImpl
import com.example.news.data.remote.dtos.ArticleDto
import com.example.news.data.repository.NewsRemoteDataSource
import com.example.news.data.validation.ArticleDtoValidator
import com.example.news.data.validation.DataValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for data source dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    /**
     * Bind remote data source implementation
     */
    @Binds
    @Singleton
    abstract fun bindNewsRemoteDataSource(
        implementation: NewsRemoteDataSourceImpl
    ): NewsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindArticleDtoValidator(
        implementation: ArticleDtoValidator
    ): DataValidator<ArticleDto>
}
