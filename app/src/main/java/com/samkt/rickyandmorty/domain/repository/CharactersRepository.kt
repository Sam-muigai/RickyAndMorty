package com.samkt.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getAllCharacters(): Flow<PagingData<CharacterInfo>>
}