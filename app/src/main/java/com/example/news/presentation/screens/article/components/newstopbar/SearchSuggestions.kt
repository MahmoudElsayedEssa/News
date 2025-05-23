package com.example.news.presentation.screens.article.components.newstopbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.news.presentation.icons.History

@Composable
fun SearchSuggestions(
    query: String, onSuggestionClick: (String) -> Unit
) {
    if (query.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.heightIn(max = 300.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(5) { index ->
                ListItem(headlineContent = {
                    Text(
                        "Search suggestion ${index + 1}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }, leadingContent = {
                    Icon(
                        Icons.Outlined.History,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }, modifier = Modifier
                    .clickable {
                        onSuggestionClick("Search suggestion ${index + 1}")
                    }
                    .animateItem())
            }
        }
    }
}
