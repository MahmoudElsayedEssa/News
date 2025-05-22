package com.example.news.domain.model.values

@JvmInline
value class Title(val value: String) {
    init {
        require(value.isNotBlank()) { "Title cannot be blank" }
        require(value.length <= 1000) { "Title too long" }
    }

    fun truncate(maxLength: Int): Title {
        return if (value.length <= maxLength) this
        else Title(value.take(maxLength - 3) + "...")
    }
}
