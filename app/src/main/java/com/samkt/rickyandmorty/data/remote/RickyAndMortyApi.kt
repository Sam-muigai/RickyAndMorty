package com.samkt.rickyandmorty.data.remote

import com.samkt.rickyandmorty.data.remote.dto.CharacterDto
import com.samkt.rickyandmorty.data.remote.dto.LocationResponseDto
import com.samkt.rickyandmorty.data.remote.dto.RickyAndMortyApiResponseDto
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
    ): CharacterDto

    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int,
    ): LocationResponseDto

    @GET("character")
    suspend fun getCharactersByName(
        @Query("name") name: String,
    ): RickyAndMortyApiResponseDto
}
