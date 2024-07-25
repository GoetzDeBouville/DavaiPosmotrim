package com.davay.android.core.domain.models

typealias timeStamp = Long

data class Session(
    val id: String,
    val users: List<User>,
    val numberOfMatchedMovies: Int?,
    val date: timeStamp,
    val status: SessionStatus,
    val imgUrl: String
)
