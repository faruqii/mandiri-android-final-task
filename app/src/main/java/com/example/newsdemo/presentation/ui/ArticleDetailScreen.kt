package com.example.newsdemo.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsdemo.presentation.viewmodel.NewsViewModel

@Composable
fun ArticleDetailScreen(
    viewModel: NewsViewModel,
    articleId: String,
    navController: NavHostController,
    appName: String
) {
    val article = viewModel.getArticleById(articleId) // Implement this method to get the article by ID

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = appName,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        article?.let {
            ArticleDetail(it, Modifier.padding(innerPadding))
        } ?: run {
            Text("Article not found", Modifier.padding(innerPadding))
        }
    }
}



