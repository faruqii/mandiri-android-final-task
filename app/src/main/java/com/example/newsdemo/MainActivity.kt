package com.example.newsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.newsdemo.presentation.viewmodel.NewsViewModel
import com.example.newsdemo.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsdemo.data.repository.NewsRepository
import com.example.newsdemo.controller.NewsNavGraph
import com.example.newsdemo.presentation.ui.theme.NewsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = NewsRepository()
        val getTopHeadlinesUseCase = GetTopHeadlinesUseCase(repository)
        val newsViewModelFactory = NewsViewModelFactory(getTopHeadlinesUseCase)
        val newsViewModel: NewsViewModel by viewModels { newsViewModelFactory }

        setContent {
            NewsDemoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NewsNavGraph(navController = navController, viewModel = newsViewModel, appName = "Mandiri News")
                }
            }
        }
    }
}


// Create a ViewModelFactory for NewsViewModel
class NewsViewModelFactory(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(getTopHeadlinesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
