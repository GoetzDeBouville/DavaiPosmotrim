package com.davay.android.domain.models

import java.util.Date

data class Session(
    val id: String,
    val users: List<User>,
    val numberOfMatchedMovies: Int?,
    val date: Date?,
    val status: SessionStatus,
    val imgUrl: String
)
