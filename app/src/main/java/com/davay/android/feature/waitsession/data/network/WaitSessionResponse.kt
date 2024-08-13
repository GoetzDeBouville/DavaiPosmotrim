package com.davay.android.feature.waitsession.data.network

import com.davay.android.core.data.dto.CollectionDto

sealed interface WaitSessionResponse {
    class CollectionList(val value: List<CollectionDto>) : WaitSessionResponse
}