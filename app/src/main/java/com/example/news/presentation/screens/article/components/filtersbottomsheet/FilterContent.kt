package com.example.news.presentation.screens.article.components.filtersbottomsheet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import com.example.news.presentation.icons.Category
import com.example.news.presentation.icons.Public
import com.example.news.presentation.icons.Sort

@Composable
fun FilterContent(
    selectedCategory: NewsCategory?,
    selectedCountry: Country?,
    selectedSortBy: SortBy,
    onCategorySelected: (NewsCategory?) -> Unit,
    onCountrySelected: (Country?) -> Unit,
    onSortBySelected: (SortBy) -> Unit,
    onApply: () -> Unit,
    isClosing: Boolean
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isClosing) 0f else 1f,
        animationSpec = tween(300),
        label = "content_alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
            .graphicsLayer { alpha = animatedAlpha }
    ) {
        // Header Section
        FilterHeader()

        Spacer(modifier = Modifier.height(32.dp))

        // Category Filter Section
        FilterSection(
            title = "Category",
            subtitle = "Choose your preferred news category",
            icon = Icons.Outlined.Category
        ) {
            CategoryFilterChips(
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Sort Filter Section
        FilterSection(
            title = "Sort by",
            subtitle = "Order articles by your preference",
            icon = Icons.Rounded.Sort
        ) {
            SortFilterChips(
                selectedSortBy = selectedSortBy,
                onSortBySelected = onSortBySelected
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Country Filter Section (Optional)
        FilterSection(
            title = "Region",
            subtitle = "Filter by specific countries",
            icon = Icons.Filled.Public
        ) {
            CountryFilterChips(
                selectedCountry = selectedCountry,
                onCountrySelected = onCountrySelected
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Action Buttons
        FilterActions(onApply = onApply)
    }
}
