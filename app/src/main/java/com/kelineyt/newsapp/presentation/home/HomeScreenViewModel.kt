package com.kelineyt.newsapp.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import com.kelineyt.newsapp.data.model.News
import com.kelineyt.newsapp.data.response.NewsResponse
import com.kelineyt.newsapp.domain.usecase.GetNewsUseCase
import com.kelineyt.newsapp.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

    private val _state = MutableStateFlow<State<NewsResponse>>(State.Loading)
    val state = _state as StateFlow<State<NewsResponse>>

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            _state.tryEmit(State.Loading)
            try {
                val result = getNewsUseCase.invoke("en", null, null)
                _state.tryEmit(State.Success(result))
            } catch (e: Exception) {
                _state.tryEmit(State.Error(e.toString()))
            }
        }
    }
}