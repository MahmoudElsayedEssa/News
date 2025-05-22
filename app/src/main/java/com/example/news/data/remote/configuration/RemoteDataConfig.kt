package com.example.souhoolatask.data.remote.configuration

/**
 * Configuration constants for the remote data layer
 */
object RemoteDataConfig {
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 100
    const val DEFAULT_TIMEOUT_SECONDS = 30L
    const val CACHE_SIZE_MB = 10
    const val CACHE_MAX_AGE_MINUTES = 5

    // Supported parameters
    val SUPPORTED_LANGUAGES = listOf("ar", "de", "en", "es", "fr", "he", "it", "nl", "no", "pt", "ru", "sv", "ud", "zh")
    val SUPPORTED_COUNTRIES = listOf("ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za")
    val SUPPORTED_CATEGORIES = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
    val SUPPORTED_SORT_OPTIONS = listOf("relevancy", "popularity", "publishedAt")
}