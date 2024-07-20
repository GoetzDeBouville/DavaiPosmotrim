package com.davay.android.domain.models

typealias timeStamp = Long
typealias userName = String

data class Session(
    val id: String,
    val users: List<userName>,
    val numberOfMatchedMovies: Int?,
    val date: timeStamp,
    val status: SessionStatus,
    val imgUrl: String
)
