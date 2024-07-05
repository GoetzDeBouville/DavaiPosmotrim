package com.davay.android.feature.coincidences.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.MovieCardView
import com.davay.android.domain.models.MovieDetails

class MoviesGridViewHolder(private val view: MovieCardView) : RecyclerView.ViewHolder(view) {

    fun bind(movie: MovieDetails) = with(view) {
        setMovieCover(movie.imgUrl ?: "")
        setMovieTitle(movie.name)
    }

    companion object {
        val create: (ViewGroup) -> MoviesGridViewHolder = { parent ->
            val view = MovieCardView(parent.context)
            MoviesGridViewHolder(view)
        }
    }
}