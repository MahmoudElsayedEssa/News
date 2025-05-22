package com.example.news.domain.exceptions

/**
 * Data-related exceptions
 */
sealed class DataException(message: String, cause: Throwable? = null) : NewsDomainException(message, cause)

class DataParsingException(message: String, cause: Throwable? = null) : DataException(message, cause)

class DataValidationException(message: String) : DataException(message)

class DataNotFoundException(message: String = "Requested data not found") : DataException(message)
