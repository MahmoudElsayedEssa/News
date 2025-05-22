package com.example.news.domain.model.values

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

    fun isWithinLast24Hours(): Boolean {
        // This would need actual date parsing in implementation
        // For now, returning false as placeholder
        return false
    }

    fun toEpochMillis(): Long {
        // Implementation would parse ISO string to epoch
        return 0L
    }
}
