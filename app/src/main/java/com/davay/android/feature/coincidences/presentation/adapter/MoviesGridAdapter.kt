package com.davay.android.feature.coincidences.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davai.util.debounceUnitFun
import com.davay.android.core.domain.models.MovieDetails
import kotlinx.coroutines.CoroutineScope

class MoviesGridAdapter(
    private val onItemClicked: (MovieDetails) -> Unit,
    private val coroutineScope: CoroutineScope
) : RecyclerView.Adapter<MoviesGridViewHolder>() {

    private val movies = mutableListOf<MovieDetails>()
    private val debounceClick = debounceUnitFun<View>(coroutineScope)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGridViewHolder =
        MoviesGridViewHolder.create(parent).apply {
            itemView.setOnClickListener { view ->
                debounceClick(view) {
                    onItemClicked(movies[adapterPosition])
                }
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