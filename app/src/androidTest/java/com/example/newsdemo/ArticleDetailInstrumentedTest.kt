package com.example.newsdemo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsdemo.data.model.Article
import com.example.newsdemo.data.model.Source
import com.example.newsdemo.presentation.ui.ArticleDetail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDetailInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockArticle = Article(
        id = "article-id",
        source = Source(id = "source-id", name = "Source Name"),
        author = "Author Name",
        title = "Article Title",
        description = "Article Description",
        url = "http://example.com",
        urlToImage = "http://example.com/image.jpg",
        publishedAt = "2024-05-30T14:21:51Z",
        content = "Article Content"
    )

    @Test
    fun testArticleDetailDisplaysCorrectly() {
        composeTestRule.setContent {
            ArticleDetail(article = mockArticle)
        }
        composeTestRule.waitForIdle()

        // Check if title is displayed
        composeTestRule.onNodeWithTag("ArticleTitle")
            .assertIsDisplayed()

        // Check if source name is displayed
        composeTestRule.onNodeWithTag("SourceName")
            .assertIsDisplayed()

        // Check if published date is displayed
        composeTestRule.onNodeWithTag("PublishDate")
            .assertIsDisplayed()

        // Check if description is displayed
        composeTestRule.onNodeWithTag("ArticleDescription")
            .assertIsDisplayed()
    }
}
