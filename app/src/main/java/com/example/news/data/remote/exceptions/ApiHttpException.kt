package com.example.souhoolatask.data.remote.exceptions

/**
 * Base class for API HTTP exceptions
 */
sealed class ApiHttpException(val code: Int, message: String) : Exception(message)

class BadRequestException(message: String) : ApiHttpException(400, message)
class UnauthorizedException(message: String) : ApiHttpException(401, message)
class ForbiddenException(message: String) : ApiHttpException(403, message)
class RateLimitException(message: String) : ApiHttpException(429, message)
class ServerErrorException(message: String) : ApiHttpException(500, message)
class ServiceUnavailableException(message: String) : ApiHttpException(503, message)
