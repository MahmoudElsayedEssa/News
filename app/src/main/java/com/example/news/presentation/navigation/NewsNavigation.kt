package com.example.news.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.news.presentation.screens.article.ArticleListRoute
import com.example.news.presentation.screens.articledetail.ArticleDetailRoute

/**
 * Main navigation setup
 */
@Composable
fun NewsNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NewsDestination.ArticleList.route
    ) {
        composable(NewsDestination.ArticleList.route) {
            ArticleListRoute(navController = navController)
        }

        composable(
            route = NewsDestination.ArticleDetail.route,
            arguments = listOf(
                navArgument("articleUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val articleUrl = backStackEntry.arguments?.getString("articleUrl")
                ?: return@composable
            ArticleDetailRoute(
                navController = navController,
                articleUrl = Uri.decode(articleUrl)
            )
        }
    }
}
