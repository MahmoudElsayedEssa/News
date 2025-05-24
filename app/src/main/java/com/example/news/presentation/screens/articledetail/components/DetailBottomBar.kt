package com.example.news.presentation.screens.articledetail.components
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news.presentation.icons.OpenInBrowser
import com.example.news.presentation.screens.articledetail.ArticleDetailActions
import com.example.news.presentation.screens.articledetail.ArticleDetailState
import com.example.news.presentation.screens.articledetail.components.*
import com.example.news.presentation.screens.components.*

@Composable
fun DetailBottomBar(
    state: ArticleDetailState,
    actions: ArticleDetailActions
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Reading progress
            Column {
                Text(
                    text = "${(state.readingProgress * 100).toInt()}% read",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (state.estimatedReadTime > 0) {
                    Text(
                        text = "${state.estimatedReadTime} min read",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = actions.onReadMoreClick,
                    enabled = state.canShare
                ) {
                    Icon(
                        imageVector = OpenInBrowser,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Read Full Article")
                }
            }
        }
    }
}
