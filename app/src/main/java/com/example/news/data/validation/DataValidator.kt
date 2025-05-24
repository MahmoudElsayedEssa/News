package com.example.news.data.validation

interface DataValidator<T> {
    fun validate(data: T): ValidationResult<T>
}

data class ValidationResult<T>(
    val data: T?,
    val errors: List<ValidationError> = emptyList()
) {
    val isValid: Boolean get() = errors.isEmpty() && data != null

    companion object {
        fun <T> success(data: T) = ValidationResult(data, emptyList())
        fun <T> failure(errors: List<ValidationError>) = ValidationResult<T>(null, errors)
        fun <T> failure(error: ValidationError) = ValidationResult<T>(null, listOf(error))
    }
}

data class ValidationError(
    val field: String,
    val message: String,
    val code: String
)
