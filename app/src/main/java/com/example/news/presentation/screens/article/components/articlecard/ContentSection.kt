package com.example.news.presentation.screens.article.components.articlecard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.domain.model.Article
import com.example.news.presentation.screens.article.components.CategoryChip
import com.example.news.presentation.screens.article.components.filtersbottomsheet.BottomSection

@Composable
fun ContentSection(
    article: Article, hasImage: Boolean
) {
    Column(
        modifier = Modifier.padding(
            top = if (hasImage) 20.dp else 24.dp, start = 24.dp, end = 24.dp, bottom = 24.dp
        )
    ) {
        // Category Chip (if no image)
        if (!hasImage) {
            CategoryChip(sourceName = article.source.name.value)
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Title
        Text(
            text = article.title.value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Description
        article.description?.let { description ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description.value,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Bottom Section
        BottomSection(article = article)
    }
}
