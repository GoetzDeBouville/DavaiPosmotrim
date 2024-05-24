package com.davay.android.feature.selectmovie.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.ItemSwipeableMovieCardBinding

class MovieCardAdapter : RecyclerView.Adapter<MovieCardAdapter.MovieCardVH>() {
    class MovieCardVH(private val binding: ItemSwipeableMovieCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardVH {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MovieCardVH, position: Int) {
        TODO("Not yet implemented")
    }

    private companion object {
        const val GOOD_RATE_7 = 7.0f
        const val DOT_DELIMETER = " ∙ "
    }
}