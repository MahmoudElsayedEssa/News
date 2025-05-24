package com.example.news.presentation.screens.articles.components.filtersbottomsheet

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DragHandle() {
    val infiniteTransition = rememberInfiniteTransition(label = "drag_handle")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.6f, animationSpec = infiniteRepeatable(
            animation = tween(2000), repeatMode = RepeatMode.Reverse
        ), label = "drag_handle_alpha"
    )

    Surface(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .size(width = 40.dp, height = 5.dp),
        shape = RoundedCornerShape(2.5.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha)
    ) {}
}
