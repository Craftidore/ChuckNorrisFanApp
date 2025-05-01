package com.craftidore.chucknorris.ui.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.craftidore.chucknorris.ui.ErrorScreen
import com.craftidore.chucknorris.ui.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = viewModel(
        factory = CategoriesViewModel.Factory
    ),
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    onCategoryClick: (category: String) -> Unit = { s -> Unit }
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        modifier = modifier.fillMaxSize(),
        content = { innerPadding ->
            Box (modifier = Modifier.padding(innerPadding)) {
                when (val uiState = viewModel.categoriesUiState) {
                    is CategoriesUiState.Loading -> LoadingScreen()
                    is CategoriesUiState.Success -> CategoryView(
                        categories = uiState.categories,
                        onCategoryClick = onCategoryClick
                    )
                    is CategoriesUiState.Error -> ErrorScreen(
                        text = "An error occurred. Check your internet connection.",
                        onRefresh = { viewModel.getCategories() }
                    )
                }
            }
        }
    )
}

@Composable
fun CategoryView(
    modifier: Modifier = Modifier,
    categories: List<String>,
    onCategoryClick: (category: String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = categories) { item ->
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(7.dp),
                onClick = { onCategoryClick(item) }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.text/capitalize.html
                    val capitalizedItem = item.replaceFirstChar {
                        if (it.isLowerCase()) {
                            it.titlecase(java.util.Locale.getDefault())
                        }
                        else {
                            it.toString()
                        }
                    }
                    Text(capitalizedItem, fontSize = 35.sp)
                }
            }
        }
    }
}

