package com.example.newsdemo.presentation.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.newsdemo.presentation.viewmodel.NewsViewModel

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val viewModel = viewModel<NewsViewModel>()
    val appName = "NewsApp" // Define your app name

    NewsNavGraph(navController = navController, viewModel = viewModel, appName = appName)
}
