package com.example.souhoolatask.data.repository

/**
 * Facade that combines all repository operations
 * Provides a unified interface for the presentation layer
 */
//@Singleton
//class NewsRepositoryFacade @Inject constructor(
//    private val newsRepository: NewsRepository,
//    private val pagingRepository: NewsPagingRepository,
//    private val preferencesRepository: UserPreferencesRepository,
//    private val offlineRepository: OfflineFirstNewsRepository
//) {
//
//    // Traditional Result-based operations
//    suspend fun getTopHeadlines(
//        page: Int,
//        pageSize: Int = DEFAULT_PAGE_SIZE,
//        query: String? = null,
//        sortBy: SortBy? = null,
//        category: NewsCategory? = null,
//        country: Country? = null
//    ): Result<ArticlesPage> {
//        val effectiveSortBy = sortBy ?: preferencesRepository.getPreferredSortBy()
//        val effectiveCategory = category ?: preferencesRepository.getPreferredCategory()
//        val effectiveCountry = country ?: preferencesRepository.getPreferredCountry()
//
//        return newsRepository.getTopHeadlines(
//            page = page,
//            pageSize = pageSize,
//            query = query,
//            sortBy = effectiveSortBy,
//            category = effectiveCategory,
//            country = effectiveCountry
//        )
//    }
//
//    // Paging-based operations
//    fun getTopHeadlinesPaging(
//        category: NewsCategory? = null, country: Country? = null, pageSize: Int = DEFAULT_PAGE_SIZE
//    ): Flow<PagingData<Article>> {
//        return pagingRepository.getTopHeadlinesPaging(category, country, pageSize)
//    }
//
//    fun getSearchResultsPaging(
//        query: String, sortBy: SortBy = SortBy.RELEVANCY, pageSize: Int = DEFAULT_PAGE_SIZE
//    ): Flow<PagingData<Article>> {
//        return pagingRepository.getSearchResultsPaging(query, sortBy, pageSize)
//    }
//
//    // Offline-first operations
//    suspend fun getArticlesCacheFirst(queryKey: String): Flow<List<Article>> {
//        return offlineRepository.getArticlesCacheFirst(queryKey)
//    }
//
//    suspend fun getArticlesNetworkFirst(queryKey: String): Result<List<Article>> {
//        return offlineRepository.getArticlesNetworkFirst(queryKey)
//    }
//
//    // User preferences
//    suspend fun getUserPreferences(): UserPreferences {
//        return UserPreferences(
//            sortBy = preferencesRepository.getPreferredSortBy(),
//            category = preferencesRepository.getPreferredCategory(),
//            country = preferencesRepository.getPreferredCountry(),
//            sources = preferencesRepository.getPreferredSources(),
//            readArticles = preferencesRepository.getReadArticles()
//        )
//    }
//
//    suspend fun updateUserPreferences(preferences: UserPreferences) {
//        preferencesRepository.setPreferredSortBy(preferences.sortBy)
//        preferencesRepository.setPreferredCategory(preferences.category)
//        preferencesRepository.setPreferredCountry(preferences.country)
//        preferencesRepository.setPreferredSources(preferences.sources)
//    }
//
//    suspend fun markArticleAsRead(articleId: ArticleId) {
//        preferencesRepository.markArticleAsRead(articleId)
//    }
//
//    // Cache management
//    suspend fun refreshCache() {
//        pagingRepository.refresh()
//    }
//
//    suspend fun clearCache() {
//        pagingRepository.clearCache()
//    }
//}
