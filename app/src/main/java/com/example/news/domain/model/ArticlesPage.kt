package com.example.news.domain.model

/**
 * Domain entity representing paginated article results
 */
data class ArticlesPage(
    val articles: List<Article>,
    val pagination: PaginationInfo
) {
    companion object {
        fun create(
            articles: List<Article>,
            currentPage: Int,
            totalResults: Int,
            pageSize: Int
        ): ArticlesPage {
            return ArticlesPage(
                articles = articles,
                pagination = PaginationInfo.Companion.create(
                    currentPage = currentPage,
                    totalResults = totalResults,
                    pageSize = pageSize,
                    hasContent = articles.isNotEmpty()
                )
            )
        }

        fun empty(): ArticlesPage = ArticlesPage(
            articles = emptyList(),
            pagination = PaginationInfo.Companion.empty()
        )
    }

    /**
     * Business rules for pagination
     */
    fun isEmpty(): Boolean = articles.isEmpty()
    fun hasMorePages(): Boolean = pagination.hasNextPage
    fun getTotalPages(): Int = pagination.totalPages
}
