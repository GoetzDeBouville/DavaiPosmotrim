package com.davay.android.feature.waitsession.data.network

import com.davay.android.core.data.dto.CollectionDto
import com.davay.android.core.data.dto.GenreDto

sealed interface WaitSessionResponse {
    class CollectionList(val value: List<CollectionDto>) : WaitSessionResponse
}