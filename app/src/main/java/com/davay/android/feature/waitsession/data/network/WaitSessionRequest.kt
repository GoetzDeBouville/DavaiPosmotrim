package com.davay.android.feature.waitsession.data.network

sealed interface WaitSessionRequest {
    val path: String

    data object Session : WaitSessionRequest {
        override val path: String = "api/collections/"
    }
}