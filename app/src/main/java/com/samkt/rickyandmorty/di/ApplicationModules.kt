package com.samkt.rickyandmorty.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samkt.rickyandmorty.data.remote.RickyAndMortyApi
import com.samkt.rickyandmorty.data.repository.CharactersRepositoryImpl
import com.samkt.rickyandmorty.data.repository.SingleCharacterRepositoryImpl
import com.samkt.rickyandmorty.domain.repository.CharactersRepository
import com.samkt.rickyandmorty.domain.repository.SingleCharacterRepository
import com.samkt.rickyandmorty.util.Constants.BASE_URL
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppModule {
    fun rickyAndMortyApi(): RickyAndMortyApi

    fun charactersRepository(): CharactersRepository

    fun singleCharacterRepository(): SingleCharacterRepository
}

class AppModuleImplementation : AppModule {
    @OptIn(ExperimentalSerializationApi::class)
    override fun rickyAndMortyApi(): RickyAndMortyApi {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(RickyAndMortyApi::class.java)
    }

    override fun charactersRepository(): CharactersRepository {
        return CharactersRepositoryImpl(rickyAndMortyApi())
    }

    override fun singleCharacterRepository(): SingleCharacterRepository {
        return SingleCharacterRepositoryImpl(rickyAndMortyApi())
    }

}












