package com.davay.android.core.domain.models

import com.davay.android.extensions.timeStamp
import kotlinx.serialization.Serializable

@Serializable
data class SessionShort(
    val id: String,
    val users: List<userName>,
    val date: timeStamp,
    val imgUrl: String
)