package com.example.news.domain.model.values

@JvmInline
value class Description(val value: String) {
    init {
        require(value.isNotBlank()) { "Description cannot be blank" }
    }

    fun truncate(maxLength: Int): Description {
        return if (value.length <= maxLength) this
        else Description(value.take(maxLength - 3) + "...")
    }
}
