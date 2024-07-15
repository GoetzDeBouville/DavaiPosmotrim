package com.davay.android.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("name") val name: String,
    @SerializedName("device_id") val userId: String
)