package com.example.souhoolatask.data.repository

///**
// * Repository with advanced offline-first capabilities
// * Provides different cache strategies based on use case
// */
//@Singleton
//class OfflineFirstNewsRepository @Inject constructor(
//    private val remoteDataSource: NewsRemoteDataSource,
//    private val localDataSource: NewsLocalDataSource,
//    private val connectivityManager: ConnectivityManager
//) {
//
//    /**
//     * Get articles with cache-first strategy
//     * Returns cached data immediately, then updates from network
//     */
//    suspend fun getArticlesCacheFirst(queryKey: String): Flow<List<Article>> = flow {
//        // Emit cached data first
//        try {
//            val cachedEntities = localDataSource.getArticlesByQuery(queryKey)
//            if (cachedEntities.isNotEmpty()) {
//                val cachedArticles = cachedEntities.mapNotNull { entity ->
//                    EntityMapper.mapEntityToDomain(entity).getOrNull()
//                }
//                emit(cachedArticles)
//            }
//        } catch (e: Exception) {
//            // Continue to network call even if cache fails
//        }
//
//        // Then try to update from network if connected
//        if (isNetworkAvailable()) {
//            try {
//                val networkResponse = remoteDataSource.getTopHeadlines(page = 1, pageSize = 20)
//                networkResponse.onSuccess { responseDto ->
//                    val entities = responseDto.articles.map { dto ->
//                        EntityMapper.mapDtoToEntity(dto, queryKey)
//                    }
//                    localDataSource.refreshArticles(queryKey, entities)
//
//                    val freshArticles = entities.mapNotNull { entity ->
//                        EntityMapper.mapEntityToDomain(entity).getOrNull()
//                    }
//                    emit(freshArticles)
//                }
//            } catch (e: Exception) {
//                // Network failed, but we already emitted cache data
//            }
//        }
//    }
//
//    /**
//     * Get articles with network-first strategy
//     * Tries network first, falls back to cache on failure
//     */
//    suspend fun getArticlesNetworkFirst(queryKey: String): Result<List<Article>> {
//        return if (isNetworkAvailable()) {
//            // Try network first
//            try {
//                val networkResponse = remoteDataSource.getTopHeadlines(page = 1, pageSize = 20)
//                networkResponse.fold(
//                    onSuccess = { responseDto ->
//                        val entities = responseDto.articles.map { dto ->
//                            EntityMapper.mapDtoToEntity(dto, queryKey)
//                        }
//                        localDataSource.refreshArticles(queryKey, entities)
//
//                        val articles = entities.mapNotNull { entity ->
//                            EntityMapper.mapEntityToDomain(entity).getOrNull()
//                        }
//                        Result.success(articles)
//                    },
//                    onFailure = { networkError ->
//                        // Network failed, try cache
//                        getCachedArticles(queryKey).fold(
//                            onSuccess = { cachedArticles ->
//                                if (cachedArticles.isNotEmpty()) {
//                                    Result.success(cachedArticles)
//                                } else {
//                                    Result.failure(networkError)
//                                }
//                            },
//                            onFailure = { Result.failure(networkError) }
//                        )
//                    }
//                )
//            } catch (e: Exception) {
//                getCachedArticles(queryKey)
//            }
//        } else {
//            // No network, go straight to cache
//            getCachedArticles(queryKey)
//        }
//    }
//
//    private suspend fun getCachedArticles(queryKey: String): Result<List<Article>> {
//        return try {
//            val entities = localDataSource.getArticlesByQuery(queryKey)
//            val articles = entities.mapNotNull { entity ->
//                EntityMapper.mapEntityToDomain(entity).getOrNull()
//            }
//            Result.success(articles)
//        } catch (e: Exception) {
//            Result.failure(DataNotFoundException("No cached articles found"))
//        }
//    }
//
//    private fun isNetworkAvailable(): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val network = connectivityManager.activeNetwork
//            val capabilities = connectivityManager.getNetworkCapabilities(network)
//            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
//        } else {
//            @Suppress("DEPRECATION")
//            connectivityManager.activeNetworkInfo?.isConnected == true
//        }
//    }
//}
