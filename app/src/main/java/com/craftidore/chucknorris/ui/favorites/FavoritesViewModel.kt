package com.craftidore.chucknorris.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.craftidore.chucknorris.ChuckNorrisFanApp
import com.craftidore.chucknorris.data.db.InternalJoke
import com.craftidore.chucknorris.data.db.InternalJokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val internalJokeRepository: InternalJokeRepository
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ChuckNorrisFanApp);
                FavoritesViewModel(application.internalJokeRepository)
            }
        }
    }

    private val favoriteQuestions = internalJokeRepository.getInternalJokes()

    // https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/map.html
    val transformedFlow = favoriteQuestions.map()
    { favQs ->
        FavoritesScreenUiState(
            favQs
        )
    }

    val uiState: StateFlow<FavoritesScreenUiState> = transformedFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = FavoritesScreenUiState()
    )

    fun removeJoke(internalJoke: InternalJoke) {
        viewModelScope.launch(Dispatchers.IO) {
            internalJokeRepository.deleteInternalJoke(
                internalJoke
            )
        }
    }
}

data class FavoritesScreenUiState(
    val favoritesList: List<InternalJoke> = emptyList(),
)