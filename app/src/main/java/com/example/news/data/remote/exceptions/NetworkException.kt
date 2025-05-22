package com.example.souhoolatask.data.remote.exceptions

/**
 * Base class for all network-related exceptions
 */
sealed class NetworkException(message: String, cause: Throwable? = null) : Exception(message, cause)

class NoConnectivityException(message: String) : NetworkException(message)
class NetworkTimeoutException(message: String) : NetworkException(message)
class NetworkConnectionException(message: String) : NetworkException(message)
class NetworkHostException(message: String) : NetworkException(message)
