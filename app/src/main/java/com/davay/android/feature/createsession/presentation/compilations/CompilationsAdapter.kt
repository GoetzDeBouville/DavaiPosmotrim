package com.davay.android.feature.createsession.presentation.compilations

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.MovieSelectionView
import com.davay.android.feature.createsession.domain.model.Compilation

class CompilationsAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<CompilationsViewHolder>() {

    val itemList: MutableList<Compilation> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompilationsViewHolder {
        val movieSelectionView = MovieSelectionView(parent.context)
        movieSelectionView.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        return CompilationsViewHolder(
            movieSelectionView,
            clickListener
        )
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: CompilationsViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun interface ItemClickListener {
        fun onClick(compilation: Compilation)
    }
}