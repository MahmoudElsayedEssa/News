package com.example.news.domain.exceptions

/**
 * API-related exceptions
 */
sealed class ApiException(message: String, cause: Throwable? = null) : NewsDomainException(message, cause)

class AuthenticationException(message: String = "Invalid API credentials") : ApiException(message)

class AuthorizationException(message: String = "Access denied") : ApiException(message)

class RateLimitExceededException(message: String = "API rate limit exceeded") : ApiException(message)

class ApiUnavailableException(message: String = "API service unavailable") : ApiException(message)

class InvalidRequestException(message: String) : ApiException(message)

class ApiUnknownException(message: String, cause: Throwable? = null) : ApiException(message, cause)
