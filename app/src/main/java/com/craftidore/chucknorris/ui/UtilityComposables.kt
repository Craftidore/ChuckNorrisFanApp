package com.craftidore.chucknorris.ui

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Taken from https://github.com/fmccown/MAD/blob/main/HeartCats/app/src/main/java/com/zybooks/heartcats/ui/CatsApp.kt
@Composable
fun LoadingScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(60.dp)
        )
        Text(
            text = "Loading...",
            fontSize = 30.sp,
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    text: String = "An Error Occurred",
    onRefresh: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text)
        Button(
            onClick = onRefresh
        ) {
            Text(
                text = "Refresh"
            )
        }
    }
}