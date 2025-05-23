package com.example.news.domain.model.values

@JvmInline
value class Content(val value: String) {

    fun isEmpty(): Boolean = value.isBlank()
    fun isNotEmpty(): Boolean = !isEmpty()

    fun estimatedReadingTimeMinutes(): Int {
        val wordsPerMinute = 200
        return maxOf(1, wordCount() / wordsPerMinute)
    }

    fun wordCount(): Int {
        return value.trim()
            .split("\\s+".toRegex())
            .count { it.isNotBlank() }
    }
}
