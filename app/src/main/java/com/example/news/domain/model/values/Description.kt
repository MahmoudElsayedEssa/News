package com.example.news.domain.model.values

@JvmInline
value class Description(val value: String) {

    fun truncate(maxLength: Int): Description {
        return if (value.length <= maxLength) this
        else Description(value.take(maxLength - 3) + "...")
    }
}
