package com.davay.android.core.domain.models

sealed interface UserDataFields {
    data class UserId(val userId: String = "") : UserDataFields
    data class UserName(val userName: String = "") : UserDataFields
}