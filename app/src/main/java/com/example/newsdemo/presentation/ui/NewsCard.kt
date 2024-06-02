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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsdemo.data.model.Article
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NewsCard(article: Article, onClick: () -> Unit) {
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
                    modifier = Modifier.size(100.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            // Content on the right side
            Column(modifier = Modifier.weight(1f)) {
                // Title
                Text(text = article.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
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