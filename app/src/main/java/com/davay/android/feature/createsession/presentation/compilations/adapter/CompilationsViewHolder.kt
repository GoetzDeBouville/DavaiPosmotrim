package com.davay.android.feature.createsession.presentation.compilations.adapter

import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.CompilationsItemBinding
import com.davay.android.feature.createsession.domain.model.Compilation

class CompilationsViewHolder(
    private val binding: CompilationsItemBinding,
    private val clickListener: CompilationsAdapter.ItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(compilation: Compilation) {
        with(binding.root) {
            setThemeTitle(compilation.name)
            setThemeCover(compilation.cover)
            setOnClickListener {
                compilation.isSelected = switchSelection()
                clickListener.onClick(compilation)
            }
        }
    }
}
