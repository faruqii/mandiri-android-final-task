package com.example.newsdemo.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsdemo.data.model.Article
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArticleDetail(article: Article, modifier: Modifier = Modifier) {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val publishedAt = dateFormatter.parse(article.publishedAt)
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp).testTag("ArticleTitle")
        )
        Text(
            text = "Published by: " + article.source.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 4.dp).testTag("SourceName")
        )
        Text(
            text = "Publish Date: " + displayFormat.format(publishedAt), // Format the date as needed
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp).testTag("PublishDate")
        )
        article.urlToImage?.let { urlToImage ->
            Image(
                painter = rememberAsyncImagePainter(urlToImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(
            text = article.description ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Justify),
            modifier = Modifier.testTag("ArticleDescription")
        )
    }
}
