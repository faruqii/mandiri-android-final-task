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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    appName: String // Added parameter for app name
) {
    val articles by viewModel.articles.collectAsState()
    val selectedArticle = remember { mutableStateOf<Article?>(null) }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = appName,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            )


            // Display the latest article with a larger image
            articles.firstOrNull()?.let { latestArticle ->
                Text(
                    text = "Latest News",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LatestNewsCard(latestArticle) {
                    selectedArticle.value = latestArticle // Update the selectedArticle value
                }
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
                    ArticleCard(article = article) {
                        selectedArticle.value = article // Update the selectedArticle value
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Display article detail if selected
            selectedArticle.value?.let { article ->
                ArticleDetail(article)
            }
        }
    }
}


@Composable
fun LatestNewsCard(article: Article, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(article.urlToImage),
                contentDescription = null, // Provide content description if available
                modifier = Modifier.fillMaxWidth().height(200.dp) // Adjust size as needed
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f) // Occupy the left side
                )
                Text(
                    text = article.publishedAt,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f), // Occupy the right side
                    textAlign = TextAlign.End // Align to the end (right)
                )
            }
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
                Text(text = article.title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = article.source.name, style = MaterialTheme.typography.bodySmall)
                Text(text = article.publishedAt, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun ArticleDetail(article: Article) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = article.source.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = article.publishedAt, // Format the date as needed
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        article.urlToImage?.let { urlToImage ->
            Image(
                painter = rememberAsyncImagePainter(urlToImage),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(
            text = article.description ?: "",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}



