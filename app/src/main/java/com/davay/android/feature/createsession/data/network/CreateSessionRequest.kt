package com.davay.android.feature.createsession.data.network

sealed class CreateSessionRequest(val path: String) {

    data object CollectionList : CreateSessionRequest(path = "api/collections/")

    data object GenreList : CreateSessionRequest(path = "api/genres/")

    class Session(
        val parameter: String,
        val requestBody: List<String>,
        val userId: String
    ) : CreateSessionRequest(path = "api/sessions/")
}