package com.davay.android.feature.matchedsession.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.domain.models.MovieDetails

class MoviesGridAdapter(
    private val onItemClicked: (Int) -> Unit,
) : RecyclerView.Adapter<MoviesGridViewHolder>() {

    private val movies = mutableListOf<MovieDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGridViewHolder =
        MoviesGridViewHolder.create(parent).apply {
            itemView.setOnClickListener {
                onItemClicked(movies[adapterPosition].id)
            }
        }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesGridViewHolder, position: Int) = with(holder) {
        bind(movies[position])
    }

    fun setData(data: List<MovieDetails>) {
        val callback = MoviesGridDiffCallback(oldItems = movies, newItems = data)
        val diff = DiffUtil.calculateDiff(callback)
        movies.clear()
        movies.addAll(data)
        diff.dispatchUpdatesTo(this)
    }
}