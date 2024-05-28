package com.example.newsdemo.data.repository

import com.example.newsdemo.data.api.RetrofitClient
import com.example.newsdemo.data.model.NewsResponse
import retrofit2.Call

class NewsRepository {
    private val apiService = RetrofitClient.retrofitInstance

    fun getTopHeadlines(apiKey: String, country: String): Call<NewsResponse> {
        return apiService.getTopHeadlines(apiKey, country)
    }
}
