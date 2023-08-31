package com.samkt.rickyandmorty.data.remote

import com.samkt.rickyandmorty.data.remote.dto.RickyAndMortyApiResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickyAndMortyApi {
    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int,
    ): RickyAndMortyApiResponseDto
}
