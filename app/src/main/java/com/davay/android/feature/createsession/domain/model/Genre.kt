package com.davay.android.feature.createsession.domain.model

data class Genre(
    val id: Int,
    val name: String,
    @Suppress("DataClassShouldBeImmutable")
    var isSelected: Boolean = false,
)
