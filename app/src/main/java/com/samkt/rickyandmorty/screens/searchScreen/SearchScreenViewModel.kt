package com.samkt.rickyandmorty.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.domain.repository.CharactersRepository
import com.samkt.rickyandmorty.util.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenViewModel(
    private val repository: CharactersRepository,
) : ViewModel() {
    private var _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState: StateFlow<SearchScreenState>
        get() = _searchScreenState

    private var job: Job? = null

    fun onEvent(event: SearchScreenEvents) {
        when (event) {
            is SearchScreenEvents.OnSearchTermChange -> {
                _searchScreenState.update {
                    it.copy(
                        searchTerm = event.value,
                    )
                }
                job?.cancel()
                job = viewModelScope.launch {
                    delay(1000)
                    getCharactersByName(searchScreenState.value.searchTerm)
                }
            }

            is SearchScreenEvents.OnClearClicked -> {
                _searchScreenState.update {
                    it.copy(
                        searchTerm = "",
                    )
                }
            }
        }
    }

    private fun getCharactersByName(name: String) {
        _searchScreenState.update {
            it.copy(
                isLoading = true,
                error = null,
            )
        }
        viewModelScope.launch {
            repository.getCharacterByName(name).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _searchScreenState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message,
                            )
                        }
                    }

                    is Result.Success -> {
                        _searchScreenState.update {
                            it.copy(
                                isLoading = false,
                                characters = result.data ?: emptyList(),
                                error = null,
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class SearchScreenEvents() {
    data class OnSearchTermChange(val value: String) : SearchScreenEvents()
    object OnClearClicked : SearchScreenEvents()
}

data class SearchScreenState(
    val searchTerm: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val characters: List<CharacterInfo> = emptyList(),
)
