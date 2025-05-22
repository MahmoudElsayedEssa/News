package com.example.news.domain.model.values

@JvmInline
value class SourceId(val value: String) {
    init {
        require(value.isNotBlank()) { "Source ID cannot be blank" }
        require(value.length <= 100) { "Source ID too long" }
    }
}
