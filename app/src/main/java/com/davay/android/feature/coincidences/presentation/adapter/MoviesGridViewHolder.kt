package com.davay.android.feature.coincidences.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.MovieCardView
import com.davay.android.feature.coincidences.presentation.TestMovie

class MoviesGridViewHolder(private val view: MovieCardView) : RecyclerView.ViewHolder(view) {

    fun bind(movie: TestMovie) = with(view) {
        setMovieCover(movie.imageUrl)
        setMovieTitle(movie.title)
    }

    companion object {
        val create: (ViewGroup) -> MoviesGridViewHolder = { parent ->
            val inflater = LayoutInflater.from(parent.context)
            val view: MovieCardView = inflater
                .inflate(com.davai.uikit.R.layout.movie_card_view, parent, false) as MovieCardView
            MoviesGridViewHolder(view)
        }
    }
}