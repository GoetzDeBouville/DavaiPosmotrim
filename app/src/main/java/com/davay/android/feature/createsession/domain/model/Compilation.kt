package com.davay.android.feature.createsession.domain.model

data class Compilation(
    val id: Int,
    val name: String,
    @Suppress("DataClassShouldBeImmutable")
    var isSelected: Boolean = false,
    val cover: String,
)
