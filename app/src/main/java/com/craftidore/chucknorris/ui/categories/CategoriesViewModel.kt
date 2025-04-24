package com.craftidore.chucknorris.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftidore.chucknorris.data.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriesViewModel(): ViewModel() {

    private val categoriesList: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList<Category>())
    private val loaded = MutableStateFlow(true)

    val uiState: StateFlow<CategoriesScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CategoriesScreenUiState()
        )

    private fun transformedFlow() = combine(
        categoriesList,
        loaded
    ) { categories, loaded ->
        CategoriesScreenUiState(
            categories
        )
    }

    init {
        viewModelScope.launch {
            categoriesList.emit(
                value = listOf(
                    Category("Category 1"),
                    Category("Category 2"),
                    Category("Category 3"),
                    Category("Category 4"),
                )
            )
        }
    }

}

data class CategoriesScreenUiState(
    val categoriesList: List<Category> = emptyList()
)