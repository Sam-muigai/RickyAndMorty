package com.samkt.rickyandmorty.domain.repository

import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.util.Result
import kotlinx.coroutines.flow.Flow

interface SingleCharacterRepository {
    fun getSingleCharacter(id: Int): Flow<Result<CharacterInfo>>
}
