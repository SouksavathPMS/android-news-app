package com.kelineyt.newsapp.data.web

import com.kelineyt.newsapp.data.response.NewsResponse
import com.kelineyt.newsapp.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewApi {
    @GET("search-news")
    suspend fun getNews(
        @Query("country") country : String?,
        @Query("text") text : String?,
        @Query("language") language : String,
        @Query("api-key") apiKey : String = API_KEY
    ) : Response<NewsResponse>
}