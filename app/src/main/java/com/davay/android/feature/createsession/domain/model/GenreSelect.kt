package com.davay.android.feature.createsession.domain.model

data class GenreSelect(
    val name: String,
    @Suppress("DataClassShouldBeImmutable")
    val isSelected: Boolean = false,
)
