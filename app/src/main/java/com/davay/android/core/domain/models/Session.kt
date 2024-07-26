package com.davay.android.core.domain.models

import com.davay.android.extensions.timeStamp

typealias userName = String

data class Session(
    val id: String,
    val users: List<userName>,
    val numberOfMatchedMovies: Int?,
    val date: timeStamp,
    val status: SessionStatus,
    val imgUrl: String
)
