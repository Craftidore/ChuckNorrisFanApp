package com.craftidore.chucknorris.ui.joke

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.craftidore.chucknorris.data.joke.Joke
import com.craftidore.chucknorris.ui.ErrorScreen
import com.craftidore.chucknorris.ui.LoadingScreen
import com.craftidore.chucknorris.ui.categories.CategoriesUiState
import com.craftidore.chucknorris.ui.categories.CategoryView

@Composable
fun JokeScreen(
    modifier: Modifier = Modifier,
    viewModel: JokeViewModel = viewModel(
        factory = JokeViewModel.Factory
    ),
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    category: String
) {
    LaunchedEffect(category) {
        viewModel.getJoke(category)
    }
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        modifier = modifier.fillMaxSize(),
        content = { innerPadding ->
            Box (modifier = Modifier.padding(innerPadding)) {
                when (val uiState = viewModel.jokeUiState) {
                    is JokeUiState.Loading -> LoadingScreen()
                    is JokeUiState.Success -> JokeView(
                        joke = uiState.joke,
                        onFavorite = {
                            viewModel.favoriteJoke()
                        },
                        onNewJoke = {
                            viewModel.getJoke(category)
                        }
                    )
                    is JokeUiState.Error -> ErrorScreen(
                        text = "An error occurred. Check your internet connection.",
                        onRefresh = {
                            viewModel.getJoke(category)
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun JokeView(
    joke: Joke,
    modifier: Modifier = Modifier,
    onFavorite: () -> Unit = {},
    onNewJoke: () -> Unit = {},
) {
    Box(
        modifier = modifier.padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
            ) {
                Text(
                    joke.value,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 18.sp
                )
            }
            Row {
                IconButton(
                    onClick = onFavorite
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        modifier = Modifier.size(40.dp),
                        contentDescription = "Favorite"
                    )
                }
                IconButton(
                    onClick = onNewJoke
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        modifier = Modifier.size(40.dp),
                        contentDescription = "Load Random"
                    )
                }
            }
        }

    }
}