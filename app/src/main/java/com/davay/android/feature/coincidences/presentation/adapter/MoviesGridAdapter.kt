package com.davay.android.feature.coincidences.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.core.domain.models.MovieDetails

class MoviesGridAdapter(
    private val onItemClicked: (MovieDetails) -> Unit,
) : RecyclerView.Adapter<MoviesGridViewHolder>() {

    private val movies = mutableListOf<MovieDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGridViewHolder =
        MoviesGridViewHolder.create(parent).apply {
            itemView.setOnClickListener {
                onItemClicked(movies[adapterPosition])
            }
        }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesGridViewHolder, position: Int) = with(holder) {
        bind(movies[position])
    }

    fun setData(data: List<MovieDetails>) {
        movies.clear()
        movies.addAll(data)
    }
}