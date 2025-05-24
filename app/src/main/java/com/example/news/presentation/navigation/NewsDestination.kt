package com.example.news.presentation.navigation

import android.net.Uri

/**
 * Navigation destinations for the app
 */

sealed class NewsDestination(val route: String) {
    object ArticleList : NewsDestination("article_list")
    object ArticleDetail : NewsDestination("article_detail/{articleJson}") {
        fun createRoute(articleJson: String): String {
            return "article_detail/${Uri.encode(articleJson)}"
        }
    }
}