package com.davay.android.feature.createsession.data.network

import com.davay.android.core.data.dto.CollectionDto
import com.davay.android.core.data.dto.GenreDto
import com.davay.android.core.data.dto.SessionDto

sealed interface CreateSessionResponse {
    class CollectionList(val value: List<CollectionDto>) : CreateSessionResponse

    class GenreList(val value: List<GenreDto>) : CreateSessionResponse

    class Session(val value: SessionDto) : CreateSessionResponse
}