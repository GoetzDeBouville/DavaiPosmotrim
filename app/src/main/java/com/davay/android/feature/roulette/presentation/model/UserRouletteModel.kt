package com.davay.android.feature.roulette.presentation.model

data class UserRouletteModel(
    val id: Int,
    val name: String,
    var isConnected: Boolean = false
)
