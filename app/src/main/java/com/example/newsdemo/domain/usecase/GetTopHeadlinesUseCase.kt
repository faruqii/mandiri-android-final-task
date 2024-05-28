package com.example.newsdemo.domain.usecase

import com.example.newsdemo.data.model.NewsResponse
import com.example.newsdemo.data.repository.NewsRepository
import retrofit2.Call

class GetTopHeadlinesUseCase(private val repository: NewsRepository) {
    fun execute(apiKey: String, country: String): Call<NewsResponse> {
        return repository.getTopHeadlines(apiKey, country)
    }
}
