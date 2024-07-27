package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("name") val name: String,
    @SerialName("device_id") val userId: String
)