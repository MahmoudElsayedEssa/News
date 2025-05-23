package com.example.news.domain.model.values

@JvmInline
value class ArticleId(val value: String) {
    init {
        require(value.isNotBlank()) { "Article ID cannot be blank" }
    }
}
