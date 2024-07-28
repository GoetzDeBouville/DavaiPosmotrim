package com.davay.android.feature.createsession.presentation.genre.adapter

import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.GenreItemBinding
import com.davay.android.feature.createsession.domain.model.GenreSelect

class GenreViewHolder(
    private val binding: GenreItemBinding,
    //private val clickListener: GenreAdapter.ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: GenreSelect) = with(binding.root) {
        setText(genre.name)
        setSelectedState(genre.isSelected)
//        setOnClickListener {
//            if (genre.isSelected) {
//                binding.root.changeStyle(TagView.Companion.Style.SECONDARY_GRAY)
//                genre.isSelected = false
//            } else {
//                binding.root.changeStyle(TagView.Companion.Style.SECONDARY_GREEN)
//                genre.isSelected = true
//            }
//            clickListener.onClick(genre)
//        }

    }
}
