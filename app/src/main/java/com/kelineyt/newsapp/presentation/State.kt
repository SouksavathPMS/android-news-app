package com.kelineyt.newsapp.presentation

sealed class State<out T> {
    object Loading : State<Nothing>()
    class Error(val errorText: String) : State<Nothing>()
    class Success<T>(val data : T) : State<T>()
}