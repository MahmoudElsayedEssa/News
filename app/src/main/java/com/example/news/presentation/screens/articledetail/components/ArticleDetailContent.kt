package com.example.news.presentation.screens.articledetail.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.domain.model.Article
import com.example.news.presentation.icons.OpenInNew
import com.example.news.presentation.icons.Time
import com.example.news.presentation.screens.articledetail.FontSize
import com.example.news.presentation.utils.formatDateTime

@Composable
fun ArticleDetailContent(
    article: Article,
    fontSize: FontSize,
    onReadMoreClick: () -> Unit,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Image
        article.imageUrl?.let { imageUrl ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(imageUrl.value).crossfade(true)
                        .build(),
                    contentDescription = "Article image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    error = painterResource(R.drawable.ic_launcher_background),
                    onSuccess = {
                        // Image loaded successfully
                    })
            }
        }

        // Article Header
        ArticleHeader(
            article = article, fontSize = fontSize
        )

        // Article Meta Information
        ArticleMetaInfo(
            article = article, fontSize = fontSize
        )

        HorizontalDivider()

        // Article Content
        ArticleContent(
            article = article, fontSize = fontSize
        )

        // Author Information
        article.author?.let { author ->
            AuthorInfo(
                author = author.value, fontSize = fontSize
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Read More Button
        ReadMoreButton(
            onClick = onReadMoreClick, modifier = Modifier.fillMaxWidth()
        )

        // Additional spacing for FAB
        Spacer(modifier = Modifier.height(80.dp))
    }
}


@Composable
private fun ArticleHeader(
    article: Article, fontSize: FontSize
) {
    Text(
        text = article.title.value,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontSize = MaterialTheme.typography.headlineMedium.fontSize * fontSize.scale
        ),
        fontWeight = FontWeight.Bold,
        lineHeight = (MaterialTheme.typography.headlineMedium.lineHeight * fontSize.scale),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun ArticleMetaInfo(
    article: Article, fontSize: FontSize
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Source information
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = article.source.name.value,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = MaterialTheme.typography.labelMedium.fontSize * fontSize.scale
                    ),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        // Published date with icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Time,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = formatDateTime(article.publishedAt.value),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize * fontSize.scale
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ArticleContent(
    article: Article, fontSize: FontSize
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Description
        article.description?.let { description ->
            Text(
                text = description.value, style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize * fontSize.scale,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * fontSize.scale
                ), color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Justify
            )
        }

        // Full content
        article.content?.let { content ->
            if (content.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Text(
                        text = content.value,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize * fontSize.scale,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * fontSize.scale
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthorInfo(
    author: String, fontSize: FontSize
) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Column {
                Text(
                    text = "Written by", style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = MaterialTheme.typography.labelSmall.fontSize * fontSize.scale
                    ), color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = author, style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize * fontSize.scale
                    ), fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun ReadMoreButton(
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.OpenInNew,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Read Full Article",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}
