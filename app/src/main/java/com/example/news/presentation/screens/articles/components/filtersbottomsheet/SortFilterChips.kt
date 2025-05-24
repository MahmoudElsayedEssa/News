package com.example.news.presentation.screens.articles.components.filtersbottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.news.domain.model.enums.SortBy
import com.example.news.presentation.utils.getSortIcon

@Composable
fun SortFilterChips(
    selectedSortBy: SortBy,
    onSortBySelected: (SortBy) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp)
    ) {
        items(SortBy.entries.toTypedArray()) { sortOption ->
            FilterChip(
                selected = selectedSortBy == sortOption,
                onClick = { onSortBySelected(sortOption) },
                label = sortOption.displayName,
                icon = getSortIcon(sortOption)
            )
        }
    }
}
