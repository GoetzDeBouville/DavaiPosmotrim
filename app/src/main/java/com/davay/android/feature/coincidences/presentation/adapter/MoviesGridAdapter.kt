package com.davay.android.feature.coincidences.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.feature.coincidences.presentation.TestMovie

class MoviesGridAdapter(
    private val onItemClicked: (Int) -> Unit,
) : RecyclerView.Adapter<MoviesGridViewHolder>() {

    private val movies = mutableListOf<TestMovie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGridViewHolder =
        MoviesGridViewHolder.create(parent)

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesGridViewHolder, position: Int) = with(holder) {
        bind(movies[position])
        itemView.setOnClickListener {
            onItemClicked(movies[position].id)
        }
    }

    fun setData(data: List<TestMovie>)  {
        val callback = MoviesGridDiffCallback(oldItems = movies, newItems = data)
        val diff = DiffUtil.calculateDiff(callback)
        movies.clear()
        movies.addAll(data)
        diff.dispatchUpdatesTo(this)
    }
}