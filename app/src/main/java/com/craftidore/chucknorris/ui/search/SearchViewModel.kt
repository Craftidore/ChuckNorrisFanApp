package com.craftidore.chucknorris.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.craftidore.chucknorris.ChuckNorrisFanApp
import com.craftidore.chucknorris.data.search.Search
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.craftidore.chucknorris.data.db.InternalJokeRepository
import com.craftidore.chucknorris.data.db.JokeToInternalJoke
import com.craftidore.chucknorris.data.joke.Joke
import com.craftidore.chucknorris.data.search.SearchRepository
import com.craftidore.chucknorris.ui.joke.JokeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.w3c.dom.Text

data class SearchScreenUiState(
    val searchAPIUiState: SearchAPIUiState,
    val searchQuery: String,
)

sealed class SearchAPIUiState {
    data class Success(val search: Search): SearchAPIUiState()
    data object Error: SearchAPIUiState()
    data object Loading: SearchAPIUiState()
    data object NoSearch: SearchAPIUiState()
}

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val internalJokeRepository: InternalJokeRepository
) : ViewModel() {
    private val searchAPIUiState: MutableStateFlow<SearchAPIUiState> = MutableStateFlow(SearchAPIUiState.NoSearch)
    private val searchQuery = MutableStateFlow("")

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                var application = (this[APPLICATION_KEY] as ChuckNorrisFanApp)
                SearchViewModel(
                    application.searchRepository,
                    internalJokeRepository = application.internalJokeRepository)
            }
        }
    }

    init {
        viewModelScope.launch {
            searchQuery.collect {
                if (it == "") {
                    searchAPIUiState.emit(SearchAPIUiState.NoSearch)
                }
            }
        }
    }

    val uiState: StateFlow<SearchScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SearchScreenUiState(
                SearchAPIUiState.Loading,
                ""
            )
        )

    private fun transformedFlow() = combine(
        searchAPIUiState,
        searchQuery,
    ) { apiState, query ->
        SearchScreenUiState(
            apiState,
            query,
        )
    }

    fun updateSearchQuery(searchQuery: String) {
        viewModelScope.launch {
            internalUpdateSearchQuery(searchQuery)
        }
    }

    // Couldn't access this.searchQuery inside
    // viewModelScope.launch for some reason... so separate function
    private suspend fun internalUpdateSearchQuery(searchQuery: String) {
        if (searchQuery.trim() == "") {
            this.searchQuery.emit("")
        }
        else {
            this.searchQuery.emit(searchQuery)
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            searchAPIUiState.emit(SearchAPIUiState.Loading)
            val newState = try {
                delay(1000)
                SearchAPIUiState.Success(searchRepository.getSearch(query))
            } catch (ex: Exception) {
                SearchAPIUiState.Error
            }
            searchAPIUiState.emit(newState)
        }
    }

    fun favoriteJoke(joke: Joke) {
        val internalJoke = JokeToInternalJoke(joke)
        viewModelScope.launch(Dispatchers.IO) {
            internalJokeRepository.addInternalJoke(internalJoke)
        }
    }
}
