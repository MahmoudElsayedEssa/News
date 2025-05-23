package com.example.news.presentation.screens.article.components.newstopbar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSearchBar(
    query: String,
    isActive: Boolean,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
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
                        "Search articles, sources, topics...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    AnimatedSearchIcon(isActive = isActive)
                },
                trailingIcon = {
                    AnimatedContent(
                        targetState = query.isNotEmpty(),
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) togetherWith
                                    fadeOut(animationSpec = tween(300))
                        },
                        label = "trailing_icon"
                    ) { hasQuery ->
                        if (hasQuery) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(
                                    Icons.Outlined.Close,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                },
                colors = SearchBarDefaults.inputFieldColors(
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.focusRequester(focusRequester)
            )
        },
        expanded = isActive,
        onExpandedChange = onActiveChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(animatedShape.dp),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shadowElevation = if (isActive) 8.dp else 2.dp,
        tonalElevation = 0.dp
    ) {
        SearchSuggestions(
            query = query,
            onSuggestionClick = { suggestion ->
                onQueryChange(suggestion)
                onSearch()
            }
        )
    }
}
