//package com.example.news.presentation.screens.article.components
//
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Clear
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.SearchBar
//import androidx.compose.material3.SearchBarDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ArticleListTopBar(
//    searchQuery: String,
//    isSearchActive: Boolean,
//    onSearchQueryChange: (String) -> Unit,
//    onSearchSubmit: () -> Unit,
//    onSearchActiveChange: (Boolean) -> Unit,
//    onShowFilters: () -> Unit
//) {
//    SearchBar(
//        inputField = {
//            SearchBarDefaults.InputField(
//                query = searchQuery,
//                onQueryChange = onSearchQueryChange,
//                onSearch = { onSearchSubmit() },
//                expanded = isSearchActive,
//                onExpandedChange = onSearchActiveChange,
//                placeholder = { Text("Search articles...") },
//                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
//                trailingIcon = {
//                    Row {
//                        if (searchQuery.isNotEmpty()) {
//                            IconButton(onClick = { onSearchQueryChange("") }) {
//                                Icon(Icons.Default.Clear, contentDescription = "Clear")
//                            }
//                        }
//                        IconButton(onClick = onShowFilters) {
//                            Icon(Icons.Default.FilterList, contentDescription = "Filters")
//                        }
//                    }
//                })
//        },
//        expanded = isSearchActive,
//        onExpandedChange = onSearchActiveChange,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        // Search suggestions could go here
//    }
//}
