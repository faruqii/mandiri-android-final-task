package com.example.newsdemo.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.newsdemo.data.model.Article
import com.example.newsdemo.presentation.viewmodel.NewsViewModel
import java.text.SimpleDateFormat
import java.util.Locale


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
                    LatestNewsCard(latestArticle) {
                        navController.navigate("article_detail/${latestArticle.source.id}")
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
                        ArticleCard(article = article) {
                            navController.navigate("article_detail/${article.source.id}")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun LatestNewsCard(article: Article, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            Log.d("NewsApp", "Navigating to article with id: ${article.source.id}")
            onClick()
        }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(article.urlToImage),
                contentDescription = null, // Provide content description if available
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Adjust size as needed
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
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val publishedAt = dateFormatter.parse(article.publishedAt)
                Text(
                    text = displayFormat.format(publishedAt),
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
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            Log.d("NewsApp", "Navigating to article with id: ${article.source.id}")
            onClick()
        }) {

    Row(modifier = Modifier.padding(16.dp)) {
            // Image on the left side
            article.urlToImage?.let { urlToImage ->
                Image(
                    painter = rememberAsyncImagePainter(urlToImage),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp).fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            // Content on the right side
            Column(modifier = Modifier.weight(1f)) {
                // Title
                Text(text = article.title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))

                // Source name and date in a row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Source name on the left
                    Text(
                        text = article.source.name,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f) // Occupy the left side
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // Date on the right
                    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                    val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val publishedAt = dateFormatter.parse(article.publishedAt)
                    Text(
                        text = displayFormat.format(publishedAt),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f), // Occupy the right side
                        textAlign = TextAlign.End // Align to the end (right)
                    )
                }
            }
        }
    }
}


@Composable
fun ArticleDetail(article: Article, modifier: Modifier = Modifier) {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val publishedAt = dateFormatter.parse(article.publishedAt)
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = article.source.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = displayFormat.format(publishedAt), // Format the date as needed
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        article.urlToImage?.let { urlToImage ->
            Image(
                painter = rememberAsyncImagePainter(urlToImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(
            text = article.description ?: "",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}



