package com.samkt.rickyandmorty.screens.singleCharacterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.domain.repository.CharactersRepository
import com.samkt.rickyandmorty.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SingleCharacterViewModel(
    private val repository: CharactersRepository,
) : ViewModel() {
    private var _singleCharacterScreenState = MutableStateFlow(SingleCharacterScreenStates())
    val singleCharacterScreenStates: StateFlow<SingleCharacterScreenStates>
        get() = _singleCharacterScreenState

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            _singleCharacterScreenState.update {
                it.copy(
                    loading = true,
                )
            }
            repository.getSingleCharacter(id).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _singleCharacterScreenState.update {
                            it.copy(
                                loading = false,
                                character = result.data,
                            )
                        }
                    }

                    is Result.Error -> {
                        _singleCharacterScreenState.update {
                            it.copy(
                                loading = false,
                                errorMessage = result.message,
                            )
                        }
                    }
                }
            }
        }
    }
}

data class SingleCharacterScreenStates(
    val loading: Boolean = false,
    val character: CharacterInfo? = null,
    val errorMessage: String? = null,
)
