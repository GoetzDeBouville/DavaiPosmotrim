package com.davay.android.core.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("name") val name: String,
    @SerializedName("device_id") val userId: String
)