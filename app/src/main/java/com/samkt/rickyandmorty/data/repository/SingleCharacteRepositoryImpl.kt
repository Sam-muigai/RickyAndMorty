package com.samkt.rickyandmorty.data.repository

import com.samkt.rickyandmorty.data.remote.RickyAndMortyApi
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.domain.repository.SingleCharacterRepository
import com.samkt.rickyandmorty.util.Result
import com.samkt.rickyandmorty.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SingleCharacterRepositoryImpl(
    private val api: RickyAndMortyApi,
) : SingleCharacterRepository {
    override fun getSingleCharacter(id: Int): Flow<Result<CharacterInfo>> {
        return flow {
            emit(
                safeApiCall {
                    api.getSingleCharacter(id).toCharacterInfo()
                },
            )
        }
    }
}
