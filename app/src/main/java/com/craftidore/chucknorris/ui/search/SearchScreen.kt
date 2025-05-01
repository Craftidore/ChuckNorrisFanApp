package com.craftidore.chucknorris.ui.search

import android.provider.SyncStateContract.Columns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.craftidore.chucknorris.data.search.Search
import com.craftidore.chucknorris.ui.ErrorScreen
import com.craftidore.chucknorris.ui.LoadingScreen
import com.craftidore.chucknorris.ui.categories.CategoriesUiState
import com.craftidore.chucknorris.ui.categories.CategoryView
import com.craftidore.chucknorris.ui.joke.JokeViewModel
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.craftidore.chucknorris.data.db.InternalJoke
import com.craftidore.chucknorris.data.joke.Joke

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.Factory
    ),
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        modifier = modifier.fillMaxSize(),
        content = { innerPadding ->
            Box (modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {

                    SearchBar(
                        text = uiState.value.searchQuery,
                        onTextChanged = {
                            viewModel.updateSearchQuery(it)
                        },
                        initiateSearch = {
                            viewModel.search(uiState.value.searchQuery)
                        }
                    )
                    when (val apiState = uiState.value.searchAPIUiState) {
                        is SearchAPIUiState.NoSearch -> EmptyScreen()
                        is SearchAPIUiState.Loading -> LoadingScreen()
                        is SearchAPIUiState.Success -> SearchResultView(
                            apiState.search,
                            favoriteJoke = { joke: Joke ->
                                viewModel.favoriteJoke(joke)
                            }
                        )

                        is SearchAPIUiState.Error -> ErrorScreen(
                            text = "An error occurred. Check your internet connection.",
                            onRefresh = {
                                viewModel.search(uiState.value.searchQuery)
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChanged: (String) -> Unit = {},
    initiateSearch: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TextField(
            text,
            onTextChanged,
        )
        Button(
            onClick = initiateSearch,
            enabled = text != "",
        ) {
            Text("Search")
        }
    }
}

@Composable
fun SearchResultView(
    searchResult: Search,
    modifier: Modifier = Modifier,
    favoriteJoke: (Joke) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(
            count = searchResult.total
        ) {
            JokeView(
                searchResult.result[it],
                onFavorite = favoriteJoke
            )
        }
    }
}

@Composable
fun EmptyScreen() {

}

@Composable
fun JokeView(
    joke: Joke,
    modifier: Modifier = Modifier,
    onFavorite: (Joke) -> Unit = {},
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
                joke.value,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.8f),
                fontSize = 18.sp
            )
        }
        Column {
            IconButton(
                onClick = {
                    onFavorite(joke)
                }
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    modifier = Modifier.size(40.dp),
                    contentDescription = "Favorite"
                )
            }
        }
    }
}
