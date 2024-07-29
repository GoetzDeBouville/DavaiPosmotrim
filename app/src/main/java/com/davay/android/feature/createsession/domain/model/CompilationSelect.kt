package com.davay.android.feature.createsession.domain.model

data class CompilationSelect(
    val id: String,
    val name: String,
    val cover: String,
    val isSelected: Boolean = false,
)
