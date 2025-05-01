package com.craftidore.chucknorris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.craftidore.chucknorris.ui.ChuckNorrisNavigation
import com.craftidore.chucknorris.ui.theme.ChuckNorrisFanAppTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChuckNorrisFanAppTheme {
                ChuckNorrisNavigation()
            }
        }
    }
}