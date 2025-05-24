package com.example.news.presentation.screens.articledetail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.example.news.presentation.icons.FormatSize
import com.example.news.presentation.screens.articledetail.ArticleDetailActions
import com.example.news.presentation.screens.articledetail.ArticleDetailState
import com.example.news.presentation.screens.articledetail.FontSize

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DetailTopBar(
    state: ArticleDetailState, actions: ArticleDetailActions, scrollProgress: Float
) {
    TopAppBar(
        title = {
        AnimatedContent(
            targetState = scrollProgress > 0.3f, transitionSpec = {
                fadeIn().togetherWith(fadeOut())
            }, label = "TopBarTitle"
        ) { showTitle ->
            if (showTitle && state.articleUi != null) {
                Text(
                    text = state.articleUi.title, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            } else {
                Text("Article Details")
            }
        }
    }, navigationIcon = {
        IconButton(onClick = actions.onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
            )
        }
    }, actions = {
        // Font size button
        IconButton(
            onClick = {
                val nextSize = when (state.fontSize) {
                    FontSize.Small -> FontSize.Medium
                    FontSize.Medium -> FontSize.Large
                    FontSize.Large -> FontSize.ExtraLarge
                    FontSize.ExtraLarge -> FontSize.Small
                }
                actions.onFontSizeChange(nextSize)
            }) {
            Icon(
                imageVector = FormatSize,
                contentDescription = "Font Size: ${state.fontSize.displayName}"
            )
        }

    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent
    )
    )
}