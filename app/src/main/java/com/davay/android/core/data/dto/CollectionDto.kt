package com.davay.android.core.data.dto

import com.google.gson.annotations.SerializedName

data class CollectionDto(
    @SerializedName("slug") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("cover") val imgUrl: String
)