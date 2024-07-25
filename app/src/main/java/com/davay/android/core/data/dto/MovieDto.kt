package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName

data class MovieDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("poster") val imgUrl: String?
)