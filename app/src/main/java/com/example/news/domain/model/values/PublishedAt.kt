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
            Instant.parse(date) // Let Java's parser handle validation - more flexible
            true
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