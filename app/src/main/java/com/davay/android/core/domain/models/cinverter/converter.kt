package com.davay.android.core.domain.models.cinverter

import com.davay.android.core.domain.models.Session
import com.davay.android.core.domain.models.SessionShort

fun Session.toSessionShort() = SessionShort(
    id,
    users,
    date,
    imgUrl
)