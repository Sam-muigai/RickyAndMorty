package com.samkt.rickyandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samkt.rickyandmorty.data.paging.LocationPagingSource
import com.samkt.rickyandmorty.data.remote.RickyAndMortyApi
import com.samkt.rickyandmorty.domain.model.Location
import com.samkt.rickyandmorty.domain.repository.LocationRepository
import com.samkt.rickyandmorty.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class LocationRepositoryImpl(
    private val api: RickyAndMortyApi,
) : LocationRepository {
    override suspend fun getLocations(): Flow<PagingData<Location>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                LocationPagingSource(api)
            },
        ).flow
    }
}
