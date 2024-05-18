package com.davay.android.feature.createsession.domain.model

data class Genre(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false,
)
