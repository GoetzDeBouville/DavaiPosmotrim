package com.davay.android.feature.changename.data.network

import com.davay.android.core.domain.models.User

class ChangeNameRequest(
    val path: String = "api/users/",
    val userData: User
)