package com.example.news.domain.exceptions

/**
 * Network-related exceptions
 */
sealed class NetworkException(message: String, cause: Throwable? = null) :
    NewsDomainException(message, cause)

class NoInternetConnectionException(message: String = "No internet connection available") :
    NetworkException(message)

class NetworkTimeoutException(message: String = "Network request timed out") :
    NetworkException(message)

class NetworkUnknownException(message: String, cause: Throwable? = null) :
    NetworkException(message, cause)
