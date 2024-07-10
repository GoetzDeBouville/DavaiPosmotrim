package com.davay.android.base.usecases

interface GetSharedPreferences<T> {
    fun getSharedPreferences(key: String): T
}