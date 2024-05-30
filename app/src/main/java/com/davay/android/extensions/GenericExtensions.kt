package com.davay.android.extensions

inline fun <reified T : Number> T.toggleSign(): T {
    return when (this) {
        is Int -> -this as T
        is Long -> -this as T
        is Float -> -this as T
        is Double -> -this as T
        else -> throw UnsupportedOperationException("This type is not supported")
    }
}