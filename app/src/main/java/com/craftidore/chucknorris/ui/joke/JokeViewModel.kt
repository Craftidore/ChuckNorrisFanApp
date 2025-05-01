package com.craftidore.chucknorris.ui.joke
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.craftidore.chucknorris.ChuckNorrisFanApp
import com.craftidore.chucknorris.data.db.InternalJokeRepository
import com.craftidore.chucknorris.data.db.JokeToInternalJoke
import com.craftidore.chucknorris.data.joke.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class JokeUiState {
    data class Success(val joke: Joke): JokeUiState()
    data object Error: JokeUiState()
    data object Loading: JokeUiState()
}

class JokeViewModel(
    private val jokeRepository: JokeRepository,
    private val internalJokeRepository: InternalJokeRepository
): ViewModel() {
    var jokeUiState: JokeUiState by mutableStateOf(JokeUiState.Loading)
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ChuckNorrisFanApp);
                JokeViewModel(
                    application.jokeRepository,
                    application.internalJokeRepository)
            }
        }
    }

    fun getJoke(category: String) {
        viewModelScope.launch {
            jokeUiState = JokeUiState.Loading
            jokeUiState = try {
                delay(1000)
                JokeUiState.Success(jokeRepository.getJoke(category))
            }
            catch (ex: Exception) {
                JokeUiState.Error
            }
        }
    }

    fun favoriteJoke() {
        if (jokeUiState is JokeUiState.Success) {
            val jokeRes = jokeUiState as JokeUiState.Success
            val internalJoke = JokeToInternalJoke(jokeRes.joke)
            viewModelScope.launch(Dispatchers.IO) {
                internalJokeRepository.addInternalJoke(internalJoke)
            }
        }
    }
}