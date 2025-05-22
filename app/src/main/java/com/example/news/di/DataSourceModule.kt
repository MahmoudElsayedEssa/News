package com.example.souhoolatask.di

import com.example.souhoolatask.data.local.dataSource.NewsLocalDataSourceImpl
import com.example.souhoolatask.data.remote.datasource.NewsRemoteDataSourceImpl
import com.example.souhoolatask.data.repository.datasources.NewsLocalDataSource
import com.example.souhoolatask.data.repository.datasources.NewsRemoteDataSource
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
    abstract fun bindNewsLocalDataSource(
        implementation: NewsLocalDataSourceImpl
    ): NewsLocalDataSource
}
