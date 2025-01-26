package com.kelineyt.newsapp.domain.repository

import com.kelineyt.newsapp.data.response.NewsResponse
import retrofit2.Response


interface NewsRepository {
    suspend fun getNews(
        language: String,
        text: String?,
        country: String?
    ): Response<NewsResponse>
}