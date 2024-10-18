package com.davay.android.feature.coincidences.data.network.models

import com.davay.android.core.data.dto.MovieDto

sealed interface GetSessionResponse {
    class Session(val value: List<MovieDto>) : GetSessionResponse
}