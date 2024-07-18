package com.davay.android.domain.models

sealed interface UserDataFields {
    data class UserId(val userId: String = "") : UserDataFields
    data class UserName(val userName: String = "") : UserDataFields
}