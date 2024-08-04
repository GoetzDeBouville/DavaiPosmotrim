package com.davay.android.feature.createsession.domain.model

data class GenreSelect(
    val name: String,
    val isSelected: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenreSelect

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
