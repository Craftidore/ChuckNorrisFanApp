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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = viewModel(),
    bottomBar: @Composable () -> Unit,
    onCategoryClick: () -> Unit = { }
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = bottomBar,
        modifier = modifier.fillMaxSize(),
        content = { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(items = uiState.value.categoriesList) { item ->
                Card(
                    modifier = Modifier
                        .animateItem()
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(7.dp),
                    onClick = onCategoryClick
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(item.name, fontSize = 35.sp)
                    }
                }
            }
        }
    })
}
