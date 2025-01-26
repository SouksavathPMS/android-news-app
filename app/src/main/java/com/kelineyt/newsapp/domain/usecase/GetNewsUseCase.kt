package com.kelineyt.newsapp.domain.usecase

import com.kelineyt.newsapp.data.response.NewsResponse
import com.kelineyt.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        language: String, text: String?, country: String?
    ): NewsResponse {
        val response = newsRepository.getNews(language, text, country)

        if (!response.isSuccessful) {
            // Check the status code and throw appropriate exceptions
            when (response.code()) {
                400 -> throw IllegalArgumentException("Bad Request: ${response.message()}")
                401 -> throw SecurityException("Unauthorized: ${response.message()}")
                403 -> throw SecurityException("Forbidden: ${response.message()}")
                404 -> throw NoSuchElementException("Not Found: ${response.message()}")
                500 -> throw IllegalStateException("Internal Server Error: ${response.message()}")
                else -> throw Exception("Unexpected Error: ${response.code()} - ${response.message()}")
            }
        }

        // If response body is null, handle it explicitly
        val body = response.body()
            ?: throw NullPointerException("Response body is null for status code: ${response.code()}")

        return body
    }

}