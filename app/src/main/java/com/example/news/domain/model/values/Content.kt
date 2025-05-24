package com.example.news.domain.model.values

import kotlin.math.roundToInt

@JvmInline
value class Content(val value: String) {

    fun isEmpty(): Boolean = value.isBlank()
    fun isNotEmpty(): Boolean = !isEmpty()

    fun estimatedReadingTimeMinutes(): Int {
        val wordsPerMinute = 200
        return maxOf(1, (wordCount().toDouble() / wordsPerMinute).roundToInt())
    }

    fun wordCount(): Int {
        return value.trim()
            .split("\\s+".toRegex())
            .count { it.isNotBlank() }
    }
}
