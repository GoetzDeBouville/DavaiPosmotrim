package com.davay.android.feature.createsession.presentation

import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.MovieSelectionView
import com.davay.android.feature.createsession.domain.model.Compilation

class CompilationsViewHolder(
    private val movieSelectionView: MovieSelectionView,
    private val clickListener: CompilationsAdapter.ItemClickListener,
) : RecyclerView.ViewHolder(movieSelectionView) {
    fun bind(compilation: Compilation) {
        with(movieSelectionView) {
            setThemeTitle(compilation.name)
            setThemeCover(compilation.cover)
            setOnClickListener {
                compilation.isSelected = switchSelection()
                clickListener.onClick(compilation)
            }
        }
    }
}
