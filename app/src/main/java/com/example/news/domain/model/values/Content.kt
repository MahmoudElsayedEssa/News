package com.example.news.domain.model.values

@JvmInline
value class Content(val value: String) {
    init {
        require(value.isNotBlank()) { "Content cannot be blank" }
    }

    fun isEmpty(): Boolean = value.isBlank()
    fun isNotEmpty(): Boolean = !isEmpty()
    fun wordCount(): Int = value.split("\\s+".toRegex()).size
}
