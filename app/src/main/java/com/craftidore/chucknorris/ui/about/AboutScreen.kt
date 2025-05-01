package com.craftidore.chucknorris.ui.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val textModifier: Modifier = Modifier.padding(10.dp)
            Text("Developed by: Elliot Hodge", textModifier)
            Text("This is an app which lets users view Chuck Norris jokes " +
                    "retrieved from https://api.chucknorris.io " +
                    "and save them locally.",
                textModifier)
            Text("It also lets the user search the database of Chuck Norris " +
                    "jokes using the search API " +
                    "provided by https://api.chucknorris.io.",
                textModifier)
        }
    }
}
