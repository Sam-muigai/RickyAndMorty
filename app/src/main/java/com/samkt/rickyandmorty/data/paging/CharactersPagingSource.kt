package com.samkt.rickyandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samkt.rickyandmorty.data.remote.RickyAndMortyApi
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import timber.log.Timber

class CharactersPagingSource(
    private val api: RickyAndMortyApi,
) : PagingSource<Int, CharacterInfo>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterInfo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterInfo> {
        val currentPage = params.key ?: 1
        return try {
            val response = api.getAllCharacters(currentPage).results.map { it.toCharacterInfo() }
            Timber.d("Successfully fetched remote data")
            val nextKey = if (response.isEmpty()) null else currentPage + 1
            val prevKey = if (currentPage == 1) null else currentPage - 1
            LoadResult.Page(
                data = response,
                nextKey = nextKey,
                prevKey = prevKey,
            )
        } catch (e: Exception) {
            Timber.d(e.message.toString())
            LoadResult.Error(e)
        }
    }
}
