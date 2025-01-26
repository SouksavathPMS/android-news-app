package com.kelineyt.newsapp.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.kelineyt.newsapp.data.model.News
import com.kelineyt.newsapp.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class HomeScreenViewModel @Inject constructor(val getNewsUseCase: GetNewsUseCase): ViewModel() {

    init {
        viewModelScope.launch {
            val result = getNews()
            result.forEach {
                Log.d("TAG", it.title)
            }
        }
    }

    suspend fun getNews(): List<News> {
        return getNewsUseCase.invoke("en", null, null).news
    }
}