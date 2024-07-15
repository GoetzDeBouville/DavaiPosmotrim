package com.davay.android.feature.roulette.presentation.carouselrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.ItemSwipeableMovieCardBinding
import com.davay.android.domain.models.MovieDetails

class CarouselAdapter :
    RecyclerView.Adapter<FilmViewHolder>() {

    private val films = mutableListOf<MovieDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilmViewHolder(
            ItemSwipeableMovieCardBinding.inflate(inflater, parent, false).apply {
                civLike.visibility = View.GONE
                civRevert.visibility = View.GONE
                civSkip.visibility = View.GONE
            },
            parent.width
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val actualPosition = position % films.size
        holder.bind(films[actualPosition])
    }

    override fun getItemCount(): Int = Integer.MAX_VALUE

    fun addFilms(list: List<MovieDetails>) {
        films.clear()
        films.addAll(list)
        notifyDataSetChanged()
    }
}
