package com.example.news.presentation.screens.articles.components.filtersbottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.news.domain.model.enums.Country
import com.example.news.presentation.icons.Public

@Composable
fun CountryFilterChips(
    selectedCountry: Country?, onCountrySelected: (Country?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp,horizontal = 24.dp)
    ) {
        item {
            FilterChip(
                selected = selectedCountry == null,
                onClick = { onCountrySelected(null) },
                label = "All Regions",
                icon = Icons.Filled.Public
            )
        }

        items(Country.entries.toTypedArray().take(5)) { country -> // Show only first 5 for space
            FilterChip(
                selected = selectedCountry == country,
                onClick = { onCountrySelected(country) },
                label = country.displayName,
                icon = Icons.Rounded.LocationOn
            )
        }
    }
}
