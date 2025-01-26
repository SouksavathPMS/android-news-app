package com.kelineyt.newsapp.presentation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen() {
  val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
}