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

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
