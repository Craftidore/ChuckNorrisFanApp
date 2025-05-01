package com.craftidore.chucknorris.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.craftidore.chucknorris.ui.about.AboutScreen
import com.craftidore.chucknorris.ui.categories.CategoriesScreen
import com.craftidore.chucknorris.ui.favorites.FavoritesScreen
import com.craftidore.chucknorris.ui.joke.JokeScreen
import com.craftidore.chucknorris.ui.search.SearchScreen
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Categories

    @Serializable
    data class Joke(val category: String)

    @Serializable
    data object Favorites

    @Serializable
    data object Search

    @Serializable
    data object About
}


@Composable
fun ChuckNorrisNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val toAbout = {
        navController.navigate(Routes.About)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Categories,
        modifier = modifier
    ) {
        composable<Routes.Categories> {
            CategoriesScreen(
                topBar = {
                    ChuckNorrisTopBar(
                        "Categories",
                        onAboutClick = toAbout,
                    )
                },
                bottomBar = {
                    ChuckNorrisBottomBar(navController)
                },
                onCategoryClick = { category: String ->
                    navController.navigate(
                        Routes.Joke(category)
                    )
                }
            )
        }
        composable<Routes.Joke> { backstackEntry ->
            val jokeRoute: Routes.Joke = backstackEntry.toRoute()
            JokeScreen(
                topBar = {
                    ChuckNorrisTopBar(
                        "Chuck Norris Joke: " + jokeRoute.category,
                        canNavigateBack = true,
                        onUpClick = { navController.navigateUp() },
                        onAboutClick = toAbout,
                    )
                },
                bottomBar = {
                    ChuckNorrisBottomBar(navController)
                },
                category = jokeRoute.category
            )
        }
        composable<Routes.Favorites> {
            FavoritesScreen(
                topBar = {
                    ChuckNorrisTopBar(
                        "Favorites",
                        canNavigateBack = true,
                        onUpClick = { navController.navigateUp() },
                        onAboutClick = toAbout,
                    )
                },
                bottomBar = {
                    ChuckNorrisBottomBar(navController)
                }
            )
        }
        composable<Routes.Search> {
            SearchScreen(
                topBar = {
                    ChuckNorrisTopBar(
                        "Search",
                        canNavigateBack = true,
                        onUpClick = { navController.navigateUp() },
                        onAboutClick = toAbout,
                    )
                },
                bottomBar = {
                    ChuckNorrisBottomBar(navController)
                },
            )
        }
        composable<Routes.About> {
            AboutScreen(
                topBar = {
                    ChuckNorrisTopBar(
                        "About",
                        canNavigateBack = true,
                        onUpClick = { navController.navigateUp() },
                        showAbout = false,
                    )
                },
                bottomBar = {
                    ChuckNorrisBottomBar(navController)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChuckNorrisBottomBar(
    navController: NavController
)
{
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val navCategories = {
        navController.popBackStack()
        navController.navigate(Routes.Categories)
    }
    val navFavorites = {
        if (currentRoute?.endsWith(Routes.Favorites.toString()) == false) {
            navController.navigate(Routes.Favorites)
        }
    }
    val navSearch = {
        if (currentRoute?.endsWith(Routes.Search.toString()) == false) {
            navController.navigate(Routes.Search)
        }
    }

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute?.endsWith(Routes.Categories.toString()) == true,
            onClick = navCategories,
            icon = {
                Icon(Icons.Filled.Menu, contentDescription = "Categories")
            },
            label = {
                Text("Categories")
            }
        )
        NavigationBarItem(
            selected = currentRoute?.endsWith(Routes.Favorites.toString()) == true,
            onClick = navFavorites,
            icon = {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorites")
            },
            label = {
                Text("Favorites")
            }
        )
        NavigationBarItem(
            selected = currentRoute?.endsWith(Routes.Search.toString()) == true,
            onClick = navSearch,
            icon = {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            },
            label = {
                Text("Search")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChuckNorrisTopBar(
    title: String,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = false,
    onUpClick: () -> Unit = {},
    showAbout: Boolean = true,
    onAboutClick: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onUpClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            }
        },
        actions = {
            if (showAbout) {
                IconButton(onClick = onAboutClick) {
                    Icon(Icons.Filled.Person, "About")
                }
            }
        }
    )
}