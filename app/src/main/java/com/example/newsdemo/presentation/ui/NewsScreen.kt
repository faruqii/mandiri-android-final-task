package com.example.newsdemo.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsdemo.data.model.Article
import com.example.newsdemo.presentation.viewmodel.NewsViewModel
import androidx.compose.foundation.Image

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    appName: String // Added parameter for app name
) {
    val articles by viewModel.articles.collectAsState()

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = appName,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display the latest article with a larger image
            articles.firstOrNull()?.let { latestArticle ->
                Text(
                    text = "Latest News",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LatestNewsCard(latestArticle)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Display other articles in separate cards
            Text(
                text = "Other News",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn {
                items(articles.drop(1)) { article ->
                    ArticleCard(article = article, onClick = { /* Navigate to detail screen */ })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun LatestNewsCard(article: Article) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = { /* Navigate to detail screen */ })) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(article.urlToImage),
                contentDescription = null, // Provide content description if available
                modifier = Modifier.fillMaxWidth().height(200.dp) // Adjust size as needed
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = article.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.source.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = article.publishedAt, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(modifier = Modifier.padding(16.dp)) {
            article.urlToImage?.let { urlToImage ->
                Image(
                    painter = rememberAsyncImagePainter(urlToImage),
                    contentDescription = null, // Provide content description if available
                    modifier = Modifier.size(100.dp) // Adjust size as needed
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = article.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = article.source.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = article.publishedAt, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

