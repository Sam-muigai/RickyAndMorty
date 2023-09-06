package com.samkt.rickyandmorty.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samkt.rickyandmorty.domain.model.Location
import com.samkt.rickyandmorty.domain.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationViewModel(
    private val repository: LocationRepository,
) : ViewModel() {

    private var _locations = MutableStateFlow<PagingData<Location>>(PagingData.empty())
    val location: StateFlow<PagingData<Location>>
        get() = _locations

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            repository.getLocations().cachedIn(this).collectLatest {
                _locations.value = it
            }
        }
    }
}
