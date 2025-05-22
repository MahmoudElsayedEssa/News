package com.example.news.domain.model.enums

/**
 * Supported countries for news filtering
 */
enum class Country(val code: String, val displayName: String) {
    EG("eg", "Egypt"),
    US("us", "United States"),
    GB("gb", "United Kingdom"),
    CA("ca", "Canada"),
    AU("au", "Australia"),
    DE("de", "Germany"),
    FR("fr", "France"),
    JP("jp", "Japan"),
    IN("in", "India");

    companion object {
        fun fromCode(code: String): Country? {
            return Country.entries.find { it.code == code }
        }
    }
}
