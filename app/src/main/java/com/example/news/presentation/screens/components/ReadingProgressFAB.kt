package com.example.news.presentation.screens.components
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.news.presentation.icons.Bookmark
import com.example.news.presentation.icons.BookmarkBorder

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReadingProgressFAB(
    progress: Float,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progressColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(300, easing = EaseInOutCubic),
        label = "progress_animation"
    )

    FloatingActionButton(
        onClick = onBookmarkClick,
        modifier = modifier
            .size(56.dp)
            .drawBehind {
                drawProgressRing(
                    progress = animatedProgress,
                    progressColor = progressColor,
                    backgroundColor = backgroundColor
                )
            },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        AnimatedContent(
            targetState = isBookmarked,
            transitionSpec = {
                scaleIn().togetherWith(scaleOut())
            }
        ) { bookmarked ->
            Icon(
                imageVector = if (bookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = if (bookmarked) "Remove bookmark" else "Add bookmark"
            )
        }
    }
}

private fun DrawScope.drawProgressRing(
    progress: Float,
    progressColor: Color,
    backgroundColor: Color
) {
    val strokeWidth = 4.dp.toPx()
    val radius = (size.minDimension - strokeWidth) / 2
    val center = size.center

    // Background ring
    drawCircle(
        color = backgroundColor,
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidth)
    )

    // Progress ring
    if (progress > 0f) {
        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = 360f * progress,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            topLeft = center - androidx.compose.ui.geometry.Offset(radius, radius),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
        )
    }
}