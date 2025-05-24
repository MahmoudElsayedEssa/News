package com.example.news.presentation.screens.articles.components.newstopbar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSearchBar(
    query: String,
    isActive: Boolean,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    searchHistory: List<String>,
    onSearchHistoryItemClick: (String) -> Unit,
    onClearSearchHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedShape by animateIntAsState(
        targetValue = if (isActive) 16 else 32,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "shape"
    )

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = { onSearch() },
                expanded = isActive,
                onExpandedChange = onActiveChange,
                placeholder = {
                    Text(
                        "Search articles, sources...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    AnimatedSearchIcon(isActive = isActive)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            )
        },
        expanded = isActive,
        onExpandedChange = onActiveChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(animatedShape.dp),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shadowElevation = if (isActive) 8.dp else 2.dp
    ) {
        SearchSuggestions(
            query = query,
            searchHistory = searchHistory,
            onSuggestionClick = onSearchHistoryItemClick,
            onClearHistory = onClearSearchHistory
        )
    }
}
