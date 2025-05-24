package com.example.news.domain.configuration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfiguration @Inject constructor() {

    // Network Configuration
    val networkTimeoutSeconds: Long = 30
    val maxRetryAttempts: Int = 3
    val retryDelayMillis: Long = 1000

    // Cache Configuration
    val cacheTimeoutHours: Long = 1
    val maxCacheSize: Int = 1000
    val cacheCleanupIntervalHours: Long = 24

    // Pagination Configuration
    val defaultPageSize: Int = 20
    val maxPageSize: Int = 100
    val prefetchDistance: Int = 5

    // UI Configuration
    val searchDebounceMillis: Long = 500
    val refreshIndicatorTimeoutSeconds: Long = 5
    val errorSnackbarDurationSeconds: Long = 4

    // Feature Flags
    val enableOfflineMode: Boolean = true
    val enableAnalytics: Boolean = true
    val enablePushNotifications: Boolean = false

    // Validation Rules
    val maxTitleLength: Int = 1000
    val maxDescriptionLength: Int = 5000
    val maxContentLength: Int = 50000
    val maxAuthorLength: Int = 200

    fun isFeatureEnabled(feature: String): Boolean {
        return when (feature) {
            "offline_mode" -> enableOfflineMode
            "analytics" -> enableAnalytics
            "push_notifications" -> enablePushNotifications
            else -> false
        }
    }
}