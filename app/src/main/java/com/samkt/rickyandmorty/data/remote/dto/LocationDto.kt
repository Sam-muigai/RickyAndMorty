package com.samkt.rickyandmorty.data.remote.dto

import com.samkt.rickyandmorty.domain.model.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("created")
    val created: String,
    @SerialName("dimension")
    val dimension: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("residents")
    val residents: List<String>,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String,
) {
    fun toLocation(): Location {
        return Location(created, dimension, id, name, residents, type, url)
    }
}
