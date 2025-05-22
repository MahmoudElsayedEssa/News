package com.example.news.domain.model.values

@JvmInline
value class SourceName(val value: String) {
    init {
        require(value.isNotBlank()) { "Source name cannot be blank" }
        require(value.length <= 200) { "Source name too long" }
    }
}
