package com.davay.android.feature.createsession.domain.model

data class Compilation(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false,
    val cover: String,
)
