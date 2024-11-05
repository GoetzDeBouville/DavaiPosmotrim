package com.davay.android.core.domain.models

import android.os.Parcelable
import com.davay.android.extensions.timeStamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class SessionShort(
    val id: String,
    val users: List<userName>,
    val date: timeStamp,
    val imgUrl: String
) : Parcelable