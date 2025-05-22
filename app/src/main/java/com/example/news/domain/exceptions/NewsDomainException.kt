package com.example.news.domain.exceptions

/**
 * Base sealed class for all domain exceptions
 */
sealed class NewsDomainException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
