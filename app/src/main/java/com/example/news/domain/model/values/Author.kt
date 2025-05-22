package com.example.news.domain.model.values

@JvmInline
value class Author(val value: String) {
    init {
        require(value.isNotBlank()) { "Author name cannot be blank" }
        require(value.length <= 200) { "Author name too long" }
    }
}
