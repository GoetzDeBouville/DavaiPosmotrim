package com.davay.android.core.data.network.model

import com.davay.android.core.data.dto.UserDto

sealed class UserDataRequest {
    class Registration(
        val path: String = "api/users/",
        val userData: UserDto
    ) : UserDataRequest()
    class ChangeName(
        val path: String = "api/users/",
        val userData: UserDto
    ) : UserDataRequest()
}