package com.davay.android.feature.createsession.presentation.genre.adapter

import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.TagView.Companion.STYLE_SECONDARY_GRAY
import com.davai.uikit.TagView.Companion.STYLE_SECONDARY_GREEN
import com.davay.android.databinding.GenreItemBinding
import com.davay.android.feature.createsession.domain.model.GenreSelect

class GenreViewHolder(
    private val binding: GenreItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: GenreSelect) = with(binding.root) {
        setText(genre.name)
        setStyle(
            if (genre.isSelected) {
                STYLE_SECONDARY_GREEN
            } else {
                STYLE_SECONDARY_GRAY
            }
        )
    }
}

