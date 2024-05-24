package com.davay.android.feature.selectmovie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.davay.android.databinding.ItemSwipeableMovieCardBinding
import com.davay.android.feature.selectmovie.MovieDetailsDemo

class MovieCardAdapter : RecyclerView.Adapter<MovieCardAdapter.MovieCardVH>() {
    class MovieCardVH(private val binding: ItemSwipeableMovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieDetailsDemo) = with(binding) {
            bindImage(data)
            tvFilmTitle.text = data.movieName
            data.ratingKinopoisk?.let {
                tvMarkValue.apply {
                    text = it.toString()
                    setTextColor(
                        if (it >= GOOD_RATE_7) {
                            binding.root.context.getColor(com.davai.uikit.R.color.done)
                        } else {
                            binding.root.context.getColor(com.davai.uikit.R.color.attention)
                        }
                    )
                }
            }
            tvOriginalTitle.text = data.alternativeName ?: data.englishName ?: ""
            tvYearCountryRuntime.text = data.buildStringYearCountriesRuntime(binding.root.context)
        }

        private fun bindImage(data: MovieDetailsDemo) {
            binding.ivSelectMovieCover.load(data.posterUrl) {
                placeholder(com.davai.uikit.R.drawable.placeholder_general_80)
                error(com.davai.uikit.R.drawable.placeholder_error_theme_112)
            }
        }
    }

    private val datalist = ArrayList<MovieDetailsDemo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSwipeableMovieCardBinding.inflate(layoutInflater, parent, false)
        return MovieCardVH(binding)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: MovieCardVH, position: Int) {
        holder.bind(datalist[position])
    }

    fun setData(list: List<MovieDetailsDemo>) {
        datalist.clear()
        datalist.addAll(list)
    }

    private companion object {
        const val GOOD_RATE_7 = 7.0f
        const val DOT_DELIMETER = " ∙ "
    }
}