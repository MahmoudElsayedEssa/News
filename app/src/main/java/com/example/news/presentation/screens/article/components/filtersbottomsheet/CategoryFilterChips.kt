package com.example.news.presentation.screens.article.components.filtersbottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.presentation.icons.Apps
import com.example.news.presentation.utils.getCategoryIcon

@Composable
fun CategoryFilterChips(
    selectedCategory: NewsCategory?, onCategorySelected: (NewsCategory?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = "All Categories",
                icon = Icons.Rounded.Apps
            )
        }

        items(NewsCategory.entries.toTypedArray()) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = category.displayName,
                icon = getCategoryIcon(category)
            )
        }
    }
}
