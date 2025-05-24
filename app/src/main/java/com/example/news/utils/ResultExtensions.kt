package com.example.news.utils


inline fun <T, R> Result<T>.mapResult(transform: (T) -> R): Result<R> {
    return fold(
        onSuccess = { value ->
            try {
                Result.success(transform(value))
            } catch (e: Exception) {
                Result.failure(e)
            }
        },
        onFailure = { Result.failure(it) }
    )
}

inline fun <T, R> Result<T>.flatMapResult(transform: (T) -> Result<R>): Result<R> {
    return fold(
        onSuccess = { value -> transform(value) },
        onFailure = { Result.failure(it) }
    )
}

inline fun <T> Result<T>.onSuccessLog(message: String): Result<T> {
    if (isSuccess) {
        println("✅ $message")
    }
    return this
}

inline fun <T> Result<T>.onFailureLog(message: String): Result<T> {
    if (isFailure) {
        println("❌ $message: ${exceptionOrNull()?.message}")
    }
    return this
}
