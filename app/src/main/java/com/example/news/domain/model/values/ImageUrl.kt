package com.example.news.domain.model.values

import android.webkit.URLUtil

@JvmInline
value class ImageUrl(val value: String) {
    init {
        require(value.isNotBlank()) { "Image URL cannot be blank" }
        require(isValidUrl(value)) { "Invalid image URL format" }
    }

    private fun isValidUrl(url: String): Boolean {
        return URLUtil.isValidUrl(url)
    }
}
