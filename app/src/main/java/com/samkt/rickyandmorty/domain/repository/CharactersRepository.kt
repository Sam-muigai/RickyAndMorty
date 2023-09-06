package com.samkt.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.util.Result
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getAllCharacters(): Flow<PagingData<CharacterInfo>>
    fun getSingleCharacter(id: Int): Flow<Result<CharacterInfo>>

    fun getCharacterByName(name: String): Flow<Result<List<CharacterInfo>>>
}
