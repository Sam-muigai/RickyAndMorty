package com.samkt.rickyandmorty.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samkt.rickyandmorty.data.remote.RickyAndMortyApi
import com.samkt.rickyandmorty.data.repository.RickyAndMortyRepositoryImpl
import com.samkt.rickyandmorty.domain.repository.RickyAndMortyRepository
import com.samkt.rickyandmorty.util.Constants.BASE_URL
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppModule {
    fun provideRickyAndMortyApi(): RickyAndMortyApi

    fun provideRickyAndMortyRepository(): RickyAndMortyRepository
}

class AppModuleImplementation : AppModule {
    @OptIn(ExperimentalSerializationApi::class)
    override fun provideRickyAndMortyApi(): RickyAndMortyApi {
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

    override fun provideRickyAndMortyRepository(): RickyAndMortyRepository {
        return RickyAndMortyRepositoryImpl(provideRickyAndMortyApi())
    }
}












