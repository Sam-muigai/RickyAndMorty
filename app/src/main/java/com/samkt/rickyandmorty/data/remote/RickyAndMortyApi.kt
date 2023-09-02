package com.samkt.rickyandmorty.data.remote

import com.samkt.rickyandmorty.data.remote.dto.ResultDto
import com.samkt.rickyandmorty.data.remote.dto.RickyAndMortyApiResponseDto
import com.samkt.rickyandmorty.domain.model.CharacterInfo
import com.samkt.rickyandmorty.util.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickyAndMortyApi {
    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int,
    ): RickyAndMortyApiResponseDto

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int,
    ): ResultDto
}
