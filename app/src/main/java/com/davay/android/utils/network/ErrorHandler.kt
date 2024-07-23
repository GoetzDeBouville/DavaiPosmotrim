package com.davay.android.utils.network

interface ErrorHandler {
    fun handleErrorCode(resultCode: Int): Resource.Error
}
