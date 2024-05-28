package com.example.newsdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsdemo.data.model.Article
import com.example.newsdemo.data.model.NewsResponse
import com.example.newsdemo.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> get() = _articles

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            val call = getTopHeadlinesUseCase.execute("51c17e63c461455b9f9215fe63ff8e74", "us")
            call.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.articles?.let {
                            _articles.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    // Handle error
                }
            })
        }
    }
}
