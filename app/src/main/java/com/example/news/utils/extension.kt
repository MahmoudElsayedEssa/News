package com.example.souhoolatask.utils

inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    return fold(
        onSuccess = { value -> transform(value) },
        onFailure = { exception -> Result.failure(exception) }
    )
}

inline fun <T, R> Result<T>.mapResult(transform: (T) -> R): Result<R> {
    return fold(
        onSuccess = { value ->
            try {
                Result.success(transform(value))
            } catch (e: Exception) {
                Result.failure(e)
            }
        },
        onFailure = { exception -> Result.failure(exception) }
    )
}
