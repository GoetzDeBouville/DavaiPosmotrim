package com.davay.android.domain.models


data class Session(
    val id: String,
    val users: List<User>,
    val movieIds: List<Int>?,
    val matchedMovieIds: List<Int>?,
    val date: String,
    val status: SessionStatus
)
