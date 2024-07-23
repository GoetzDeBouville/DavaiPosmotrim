package com.davay.android.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDto(
    @SerialName("slug") val id: String,
    @SerialName("name") val name: String,
    @SerialName("cover") val imgUrl: String
)