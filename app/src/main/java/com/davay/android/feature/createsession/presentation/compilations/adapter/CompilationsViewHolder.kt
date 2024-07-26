package com.davay.android.feature.createsession.presentation.compilations.adapter

import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.CompilationsItemBinding
import com.davay.android.feature.createsession.domain.model.CompilationSelect

class CompilationsViewHolder(
    private val binding: CompilationsItemBinding,
    private val clickListener: CompilationsAdapter.ItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(compilation: CompilationSelect) = with(binding.root) {
        setThemeTitle(compilation.name)
        setThemeCover(compilation.cover)
        setSelectedState(compilation.isSelected)

        setOnClickListener {
            compilation.isSelected = !compilation.isSelected
            setSelectedState(compilation.isSelected)
            clickListener.onClick(compilation)
        }
    }
}
