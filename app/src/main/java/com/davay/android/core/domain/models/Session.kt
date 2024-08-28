package com.davay.android.core.domain.models

import android.os.Parcelable
import com.davay.android.extensions.timeStamp
import kotlinx.parcelize.Parcelize

typealias userName = String
@Parcelize
data class Session(
    val id: String,
    val users: List<userName>,
    val numberOfMatchedMovies: Int?,
    val date: timeStamp,
    val status: SessionStatus,
    val imgUrl: String
) : Parcelable
