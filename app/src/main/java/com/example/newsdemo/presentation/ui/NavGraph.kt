package com.example.newsdemo.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsdemo.presentation.viewmodel.NewsViewModel

@Composable
fun AppNavigation(viewModel: NewsViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "news_list") {
        composable("news_list") {
            NewsScreen(viewModel, "NewsApp", navController)
        }
        composable(
            route = "article_detail/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId")
            if (articleId != null) {
                ArticleDetailScreen(
                    viewModel = viewModel,
                    articleId = articleId,
                    navController = navController,
                    appName = "NewsApp" // Provide the app name
                )
            }
        }
    }
}