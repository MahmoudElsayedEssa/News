package com.example.news.domain.model

/**
 * Value object for pagination information
 */
data class PaginationInfo(
    val currentPage: Int,
    val totalResults: Int,
    val pageSize: Int,
    val totalPages: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
) {
    companion object {
        fun create(
            currentPage: Int,
            totalResults: Int,
            pageSize: Int,
            hasContent: Boolean
        ): PaginationInfo {
            require(currentPage > 0) { "Current page must be positive" }
            require(totalResults >= 0) { "Total results cannot be negative" }
            require(pageSize > 0) { "Page size must be positive" }

            val totalPages = if (totalResults == 0) 1 else (totalResults + pageSize - 1) / pageSize

            return PaginationInfo(
                currentPage = currentPage,
                totalResults = totalResults,
                pageSize = pageSize,
                totalPages = totalPages,
                hasNextPage = hasContent && currentPage < totalPages,
                hasPreviousPage = currentPage > 1
            )
        }

        fun empty(): PaginationInfo = PaginationInfo(
            currentPage = 1,
            totalResults = 0,
            pageSize = 20,
            totalPages = 1,
            hasNextPage = false,
            hasPreviousPage = false
        )
    }
}
