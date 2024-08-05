package com.davay.android.feature.changename.data.network

import com.davay.android.core.data.dto.UserDto

class ChangeNameRequest(
    val path: String = "api/users/",
    val userData: UserDto
)