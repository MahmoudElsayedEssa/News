//package com.example.news.presentation.screens.article.components
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import com.example.souhoolatask.domain.model.Article
//
//@Composable
//fun ArticleItem(
//    article: Article,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        onClick = onClick,
//        modifier = modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            // Article Image
//            article.imageUrl?.let { imageUrl ->
//                AsyncImage(
//                    model = imageUrl,
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .clip(RoundedCornerShape(8.dp)),
//                    error = painterResource(R.drawable.ic_placeholder_image)
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//            }
//
//            // Article Title
//            Text(
//                text = article.title.value,
//                style = MaterialTheme.typography.headlineSmall,
//                fontWeight = FontWeight.Bold,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Article Description
//            article.description?.let { description ->
//                Text(
//                    text = description.value,
//                    style = MaterialTheme.typography.bodyMedium,
//                    maxLines = 3,
//                    overflow = TextOverflow.Ellipsis,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//            }
//
//            // Source and Date
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Public,
//                        contentDescription = null,
//                        modifier = Modifier.size(16.dp),
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = article.source.name.value,
//                        style = MaterialTheme.typography.labelMedium,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//
//                Text(
//                    text = formatRelativeTime(article.publishedAt.value),
//                    style = MaterialTheme.typography.labelSmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        }
//    }
//}
