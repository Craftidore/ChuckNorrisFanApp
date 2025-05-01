package com.craftidore.chucknorris.ui.categories

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.craftidore.chucknorris.data.ChuckNorrisApiService
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.craftidore.chucknorris.ChuckNorrisFanApp
import com.craftidore.chucknorris.data.category.CategoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class CategoriesUiState {
    data class Success(val categories: List<String>): CategoriesUiState()
    data object Error: CategoriesUiState()
    data object Loading: CategoriesUiState()
}

class CategoriesViewModel(private val categoryRepository: CategoryRepository): ViewModel() {
    var categoriesUiState: CategoriesUiState by mutableStateOf(CategoriesUiState.Loading)
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                var application = (this[APPLICATION_KEY] as ChuckNorrisFanApp)
                CategoriesViewModel(application.categoryRepository)
            }
        }
    }

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            categoriesUiState = CategoriesUiState.Loading
            categoriesUiState = try {
                if (!categoryRepository.hasCache()) {
                    delay(1000)
                }
                CategoriesUiState.Success(categoryRepository.getCategories())
            }
            catch (ex: Exception) {
                CategoriesUiState.Error
            }
        }
    }
}

data class CategoriesScreenUiState(
    val categoriesList: List<String> = emptyList()
)