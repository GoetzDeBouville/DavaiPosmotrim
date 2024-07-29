package com.davay.android.feature.createsession.domain.model

data class CompilationSelect(
    val id: String,
    val name: String,
    val cover: String,
    val isSelected: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompilationSelect

        if (id != other.id) return false
        if (name != other.name) return false
        if (cover != other.cover) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + cover.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }
}
