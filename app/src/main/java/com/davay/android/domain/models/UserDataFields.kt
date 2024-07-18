package com.davay.android.domain.models

sealed interface UserDataFields {
    class UserId(userId: String)
    class UserName(userName: String)
}