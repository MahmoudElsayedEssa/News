package com.example.news.presentation.screens.articles.components.newstopbar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.news.presentation.icons.FilterList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopBar(
    searchQuery: String,
    isSearchActive: Boolean,
    isScrolled: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    onShowFilters: () -> Unit,
    hasActiveFilters: Boolean = false,
    searchHistory: List<String> = emptyList(),
    onSearchHistoryItemClick: (String) -> Unit = {},
    onClearSearchHistory: () -> Unit = {}
) {
    val animatedElevation by animateDpAsState(
        targetValue = if (isScrolled) 8.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "elevation"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isScrolled) 0.95f else 1f, animationSpec = tween(300), label = "alpha"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = animatedElevation,
        color = MaterialTheme.colorScheme.surface.copy(alpha = animatedAlpha)
    ) {
        Column(
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // Enhanced Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Discover",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Latest news from around the world",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                // Enhanced Filter Button with indicator
                Badge(
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                ) {
                    if (hasActiveFilters) {
                        Text(
                            text = "●", style = MaterialTheme.typography.labelSmall
                        )
                    }

                    FilledIconButton(
                        onClick = onShowFilters,
                        modifier = Modifier.size(48.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = if (hasActiveFilters) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.primaryContainer
                            }, contentColor = if (hasActiveFilters) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            }
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = "Filters",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Enhanced Search Bar
            NewsSearchBar(
                query = searchQuery,
                isActive = isSearchActive,
                onQueryChange = onSearchQueryChange,
                onSearch = onSearchSubmit,
                onActiveChange = onSearchActiveChange,
                searchHistory = searchHistory,
                onSearchHistoryItemClick = onSearchHistoryItemClick,
                onClearSearchHistory = onClearSearchHistory,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
    }
}
