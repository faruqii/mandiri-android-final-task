package com.example.newsdemo.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsdemo.data.model.Article
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HeadlineNewsCard(article: Article, onClick: () -> Unit) {
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
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
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