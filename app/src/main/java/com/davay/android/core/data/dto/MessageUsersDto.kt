package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MessageUsersDto(
    @SerialName("message")
    val userList: List<UserDto>
)