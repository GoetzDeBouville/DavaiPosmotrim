package com.davay.android.feature.registration.data.network

import com.davay.android.core.data.dto.UserDto

class RegistrationRequest(
    val path: String = "api/users/",
    val userData: UserDto
)