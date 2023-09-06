package com.samkt.rickyandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samkt.rickyandmorty.data.paging.CharactersPagingSource
import com.samkt.rickyandmorty.data.remote.RickyAndMortyApi
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.domain.repository.CharactersRepository
import com.samkt.rickyandmorty.util.Constants.ITEMS_PER_PAGE
import com.samkt.rickyandmorty.util.Result
import com.samkt.rickyandmorty.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class CharactersRepositoryImpl(
    private val api: RickyAndMortyApi,
) : CharactersRepository {
    override suspend fun getAllCharacters(): Flow<PagingData<CharacterInfo>> {
        Timber.tag("Fetching").d("Repository called")
        return Pager(
            config = PagingConfig(ITEMS_PER_PAGE),
            pagingSourceFactory = {
                CharactersPagingSource(api)
            },
        ).flow
    }

    override fun getSingleCharacter(id: Int): Flow<Result<CharacterInfo>> {
        return flow {
            emit(
                safeApiCall {
                    api.getSingleCharacter(id).toCharacterInfo()
                },
            )
        }
    }

    override fun getCharacterByName(name: String): Flow<Result<List<CharacterInfo>>> = flow {
        emit(
            safeApiCall {
                api.getCharactersByName(name).results.map { it.toCharacterInfo() }
            },
        )
    }
}




























