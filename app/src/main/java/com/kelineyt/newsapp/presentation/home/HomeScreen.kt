package com.kelineyt.newsapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kelineyt.newsapp.data.model.News
import com.kelineyt.newsapp.presentation.State

@Composable
fun HomeScreen() {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    val uiState = homeScreenViewModel.state.collectAsState();
    val searchText = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        SearchBar(searchText.value, onSearch = {
            searchText.value = it
        })
        Spacer(modifier = Modifier.height(16.dp))

        // Filter
        when (uiState.value) {
            State.Loading -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Box(modifier = Modifier.height(10.dp))
                Text(text = "Loading...")
            }

            is State.Error -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val error = (uiState.value as State.Error).errorText
                Text(text = "Failure!")
                Text(text = error)
                Button(onClick = {
                    homeScreenViewModel.getNews()
                }) {
                    Text("Retry")
                }
            }

            is State.Success -> {
                val data = (uiState.value as State.Success).data
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Text(text = "News")
                    }
                    items(data.news) { news ->
                        NewsItem(news)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(news: News) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Background Image
        AsyncImage(
            model = news.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay for Text Readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                    )
                )
        )

        // Text Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = news.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f)) // Pushes date and authors to the bottom
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = news.authors?.joinToString(", ") ?: "Unknown Author",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = news.publish_date,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun SearchBar(text: String, onSearch: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = onSearch,
            label = {
                Text("Search")
            },
            shape = RoundedCornerShape(16.dp)
        )

        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_search),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
