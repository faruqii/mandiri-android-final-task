package com.example.newsdemo.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsdemo.presentation.ui.component.CustomTopAppBar
import com.example.newsdemo.presentation.viewmodel.NewsViewModel


@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    appName: String,
    navController: NavHostController,
) {
    val articles by viewModel.articles.collectAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = appName, // Use appName as the title
                onBackClick = { /* Handle navigation click */ }
            )
        }
    ) { innerPadding ->
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                // Display the latest article with a larger image
                articles.firstOrNull()?.let { latestArticle ->
                    Text(
                        text = "Headlines",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    HeadlineNewsCard(latestArticle) {
                        Log.d("NewsApp", "Attempting to navigate to article with id: ${latestArticle.source.id}")
                        latestArticle.source.id?.let { id ->
                            try {
                                navController.navigate("article_detail/$id")
                            } catch (e: Exception) {
                                Log.e("NewsApp", "Navigation error: ${e.message}")
                            }
                        } ?: Log.e("NewsApp", "Article source id is null")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Display other articles in separate cards
                Text(
                    text = "Other News",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyColumn {
                    items(articles.drop(1)) { article ->
                        NewsCard(article = article) {
                            Log.d("NewsApp", "Attempting to navigate to article with id: ${article.source.id}")
                            article.source.id?.let { id ->
                                try {
                                    navController.navigate("article_detail/$id")
                                } catch (e: Exception) {
                                    Log.e("NewsApp", "Navigation error: ${e.message}")
                                }
                            } ?: Log.e("NewsApp", "Article source id is null")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}