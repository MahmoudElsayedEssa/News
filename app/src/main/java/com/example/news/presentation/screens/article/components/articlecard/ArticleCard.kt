package com.example.news.presentation.screens.article.components.articlecard

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.news.domain.model.Article

@Composable
fun ArticleCard(
    article: Article, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val animatedElevation by animateDpAsState(
        targetValue = if (isPressed) 1.dp else 6.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "elevation"
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    Card(
        onClick = onClick, modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    })
            }, shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = animatedElevation
        )
    ) {
        Column {
            // Hero Image Section
            article.imageUrl?.let { imageUrl ->
                HeroImageSection(
                    imageUrl = imageUrl.value, sourceName = article.source.name.value
                )
            }

            // Content Section
            ContentSection(
                article = article, hasImage = article.imageUrl != null
            )
        }
    }
}
