package com.samkt.rickyandmorty.data.remote.dto

import com.samkt.rickyandmorty.domain.model.CharacterInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultDto(
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String,
){
    fun toCharacterInfo():CharacterInfo{
        return CharacterInfo(id, image, name, status)
    }
}

