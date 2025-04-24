package com.craftidore.chucknorris.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.craftidore.chucknorris.ui.categories.CategoriesScreen
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Categories

    @Serializable
    data object Joke

    @Serializable
    data object Favorites

    @Serializable
    data object Search
}


@Composable
fun ChuckNorrisApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Categories,
        modifier = modifier
    ) {
        composable<Routes.Categories> {
            CategoriesScreen(
                bottomBar = {
                    ChuckNorrisBottomBar(
                        {},
                        {},
                        {}
                    )
                },
            )
        }
        composable<Routes.Joke> {
            Text("Joke")
        }
        composable<Routes.Favorites> {
            Text("Favorites")
        }
        composable<Routes.Search> {
            Text("Search")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChuckNorrisBottomBar(
    onCategoriesClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSearchClick: () -> Unit,
)
{
    BottomAppBar()
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
                .height(40.dp)
        ) {
            IconButton(onClick = onCategoriesClick) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Categories"
                )
            }
            IconButton(onClick = onFavoritesClick) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorites"
                )
            }
            IconButton(onClick = onSearchClick) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Favorites"
                )
            }
        }

    }
}

