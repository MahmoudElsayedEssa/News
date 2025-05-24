package com.example.news.domain.model.values

import android.webkit.URLUtil

@JvmInline
value class ArticleUrl(val value: String) {
    init {
        require(value.isNotBlank()) { "URL cannot be blank" }
        require(isValidUrl(value)) { "Invalid URL format" }
    }

    private fun isValidUrl(url: String): Boolean {
        return URLUtil.isValidUrl(url)
    }
}
