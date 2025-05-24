package com.example.news.presentation.screens.articles.components.filtersbottomsheet

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBottomSheet(
    selectedCategory: NewsCategory?,
    selectedCountry: Country?,
    selectedSortBy: SortBy,
    onCategorySelected: (NewsCategory?) -> Unit,
    onCountrySelected: (Country?) -> Unit,
    onSortBySelected: (SortBy) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var isClosing by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            isClosing = true
            onDismiss()
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 0.dp,
        dragHandle = {
            DragHandle()
        },
    ) {
        FilterContent(
            selectedCategory = selectedCategory,
            selectedCountry = selectedCountry,
            selectedSortBy = selectedSortBy,
            onCategorySelected = onCategorySelected,
            onCountrySelected = onCountrySelected,
            onSortBySelected = onSortBySelected,
            onApply = {
                isClosing = true
                onDismiss()
            },
            isClosing = isClosing
        )
    }
}
