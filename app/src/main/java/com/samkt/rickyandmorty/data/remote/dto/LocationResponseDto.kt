package com.samkt.rickyandmorty.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponseDto(
    @SerialName("results")
    val results: List<LocationDto>
)