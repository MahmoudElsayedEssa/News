package com.example.news.domain.model.values

import com.example.news.presentation.model.ArticleUi
import kotlin.math.ceil
import kotlin.math.roundToInt

@JvmInline
value class Content(val value: String) {

    fun isEmpty(): Boolean = value.isBlank()
    fun isNotEmpty(): Boolean = !isEmpty()


    fun estimatedReadingTimeMinutes(): Int {
        val baseContent = value
        val baseWordCount = baseContent.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }.size

        val extraCharsMatch = "\\[\\+(\\d+) chars]".toRegex().find(baseContent)
        val extraChars = extraCharsMatch?.groupValues?.get(1)?.toIntOrNull() ?: 0

        val estimatedExtraWords = extraChars / 6

        val totalWordCount = baseWordCount + estimatedExtraWords
        return maxOf(1, ceil(totalWordCount / 200.0).toInt())
    }
}
