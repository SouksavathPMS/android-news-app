package com.kelineyt.newsapp.data.repositoryIpml

import com.kelineyt.newsapp.data.response.NewsResponse
import com.kelineyt.newsapp.data.web.NewApi
import com.kelineyt.newsapp.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryIpml @Inject constructor(val api: NewApi) : NewsRepository {
    override suspend fun getNews(
        language: String,
        text: String?,
        country: String?
    ): Response<NewsResponse> {
        val response = api.getNews(country, text, language)
        return response
    }
}