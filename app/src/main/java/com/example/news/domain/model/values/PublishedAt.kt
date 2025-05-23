package com.example.news.domain.model.values

import java.time.Instant
import java.util.concurrent.TimeUnit

@JvmInline
value class PublishedAt(val value: String) {
    init {
        require(value.isNotBlank()) { "Published date cannot be blank" }
        require(isValidIsoDate(value)) { "Invalid ISO date format" }
    }

    private fun isValidIsoDate(date: String): Boolean {
        return try {
            // Basic ISO 8601 format validation
            date.matches(Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?Z?"))
        } catch (e: Exception) {
            false
        }
    }

    fun toEpochMillis(): Long {
        return try {
            Instant.parse(value).toEpochMilli()
        } catch (e: Exception) {
            throw IllegalStateException("Invalid date format: $value", e)
        }
    }

    fun isWithinLast24Hours(): Boolean {
        val now = System.currentTimeMillis()
        val articleTime = toEpochMillis()
        return (now - articleTime) <= TimeUnit.DAYS.toMillis(1)
    }
}
