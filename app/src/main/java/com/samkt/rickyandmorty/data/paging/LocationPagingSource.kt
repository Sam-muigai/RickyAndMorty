package com.samkt.rickyandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samkt.rickyandmorty.data.remote.RickyAndMortyApi
import com.samkt.rickyandmorty.data.remote.dto.LocationDto
import com.samkt.rickyandmorty.domain.model.Location

class LocationPagingSource(
    private val api: RickyAndMortyApi,
) : PagingSource<Int, Location>() {
    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        val currentPosition = params.key ?: 1
        return try {
            val response = api.getLocations(currentPosition).results.map { it.toLocation() }
            val nextKey = if (response.isEmpty()) null else currentPosition + 1
            val previousKey = if(currentPosition == 1) null else currentPosition - 1
            LoadResult.Page(
                data = response,
                nextKey = nextKey,
                prevKey = previousKey
            )
        }catch (e:Exception){
            LoadResult.Error(throwable = e)
        }
    }
}
