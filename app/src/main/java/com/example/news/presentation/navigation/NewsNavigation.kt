package com.example.news.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.news.presentation.screens.articledetail.ArticleDetailRoute
import com.example.news.presentation.screens.articles.ArticleListRoute

@Composable
fun NewsNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController, startDestination = NewsDestination.ArticleList.route
    ) {
        composable(NewsDestination.ArticleList.route) {
            ArticleListRoute(navController = navController)
        }

        composable(
            route = NewsDestination.ArticleDetail.route, arguments = listOf(
            navArgument("articleJson") { type = NavType.StringType })) { backStackEntry ->
            ArticleDetailRoute(navController = navController)
        }
    }
}