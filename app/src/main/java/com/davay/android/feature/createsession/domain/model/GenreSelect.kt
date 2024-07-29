package com.davay.android.feature.createsession.domain.model

data class GenreSelect(
    val name: String,
    val isSelected: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenreSelect

        if (name != other.name) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }
}
