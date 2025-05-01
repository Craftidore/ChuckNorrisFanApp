package com.craftidore.chucknorris.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.craftidore.chucknorris.data.db.InternalJoke
import com.craftidore.chucknorris.data.joke.Joke

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModel.Factory
    ),
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val favoritesList = uiState.value.favoritesList

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        modifier = modifier.fillMaxSize(),
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
            ) {
                items(
                    favoritesList.size,
                    key = {
                        favoritesList[it].id
                    }
                ) {
                    JokeView(
                        joke = favoritesList[it],
                        onUnfavorite = {
                            viewModel.removeJoke(favoritesList[it])
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun JokeView(
    joke: InternalJoke,
    modifier: Modifier = Modifier,
    onUnfavorite: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Card {
            Text(
                joke.joke,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.8f),
                fontSize = 18.sp
            )
        }
        Column {
            IconButton(
                onClick = onUnfavorite
            ) {
                Icon(
                    Icons.Filled.Delete,
                    modifier = Modifier.size(40.dp),
                    contentDescription = "Unfavorite"
                )
            }
        }
    }
}
