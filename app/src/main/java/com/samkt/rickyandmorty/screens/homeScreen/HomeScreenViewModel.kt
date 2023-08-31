package com.samkt.rickyandmorty.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.domain.repository.RickyAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeScreenViewModel(
    private val repository: RickyAndMortyRepository,
) : ViewModel() {

    private var _characters = MutableStateFlow<PagingData<CharacterInfo>>(PagingData.empty())
    val characters: StateFlow<PagingData<CharacterInfo>>
        get() = _characters

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            repository.getAllCharacters().cachedIn(this).collectLatest {
                _characters.value = it
                Timber.d(it.toString())
            }
        }
    }
}
