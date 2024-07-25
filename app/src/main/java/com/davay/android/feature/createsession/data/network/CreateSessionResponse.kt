package com.davay.android.feature.createsession.data.network

import com.davay.android.core.data.dto.CollectionDto
import com.davay.android.core.data.dto.GenreDto
import kotlinx.serialization.Serializable

sealed interface CreateSessionResponse {
    @Serializable
    class CollectionList(val value: List<CollectionDto>) : CreateSessionResponse

    @Serializable
    class GenreList(val value: List<GenreDto>) : CreateSessionResponse
}