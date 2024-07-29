package com.davay.android.feature.createsession.data.network

sealed interface CreateSessionRequest {
    val path: String

    data object CollectionList : CreateSessionRequest {
        override val path: String = "api/collections/"
    }

    data object GenreList : CreateSessionRequest {
        override val path: String = "api/genres/"
    }
}