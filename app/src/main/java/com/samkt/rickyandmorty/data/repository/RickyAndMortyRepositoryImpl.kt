package com.samkt.rickyandmorty.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samkt.rickyandmorty.data.paging.CharactersPagingSource
import com.samkt.rickyandmorty.data.remote.RickyAndMortyApi
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.domain.repository.RickyAndMortyRepository
import com.samkt.rickyandmorty.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class RickyAndMortyRepositoryImpl(
    private val api: RickyAndMortyApi,
) : RickyAndMortyRepository {
    override suspend fun getAllCharacters(): Flow<PagingData<CharacterInfo>> {
        Timber.tag("Fetching").d("Repository called")
        return Pager(
            config = PagingConfig(ITEMS_PER_PAGE),
            pagingSourceFactory = {
                CharactersPagingSource(api)
            },
        ).flow
    }
}
