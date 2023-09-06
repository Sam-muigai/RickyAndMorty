package com.samkt.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.samkt.rickyandmorty.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLocations(): Flow<PagingData<Location>>
}
