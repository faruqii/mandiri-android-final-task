package com.example.newsdemo.controller

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsdemo.presentation.ui.ArticleDetailScreen
import com.example.newsdemo.presentation.ui.NewsScreen
import com.example.newsdemo.presentation.viewmodel.NewsViewModel

@Composable
fun NewsNavGraph(
    navController: NavHostController,
    viewModel: NewsViewModel,
    appName: String // Added app name parameter
) {
    NavHost(navController = navController, startDestination = "news_screen") {
        composable("news_screen") {
            NewsScreen(viewModel = viewModel, appName = appName, navController = navController)
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
                    appName = appName // Pass app name to ArticleDetailScreen
                )
            }
        }
    }
}

