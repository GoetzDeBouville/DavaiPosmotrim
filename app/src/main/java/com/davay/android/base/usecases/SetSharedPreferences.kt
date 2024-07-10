package com.davay.android.base.usecases

interface SetSharedPreferences<T> {
    fun setSharedPreferences(key: String, value: T)
}