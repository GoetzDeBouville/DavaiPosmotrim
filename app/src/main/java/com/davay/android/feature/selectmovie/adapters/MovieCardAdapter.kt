package com.davay.android.feature.selectmovie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.ItemSwipeableMovieCardBinding
import com.davay.android.feature.selectmovie.MovieDetailsDemo
import com.davay.android.utils.MovieDetailsHelper
import com.davay.android.utils.MovieDetailsHelperImpl

class MovieCardAdapter(
    private val swipeLeft: () -> Unit,
    private val swipeRight: () -> Unit,
    private val revert: () -> Unit,
    private val inflateMovieDetails: (MovieDetailsDemo) -> Unit
) : RecyclerView.Adapter<MovieCardAdapter.MovieCardVH>() {
    inner class MovieCardVH(
        private val binding: ItemSwipeableMovieCardBinding,
        private val swipeLeft: () -> Unit,
        private val swipeRight: () -> Unit,
        private val revert: () -> Unit,
        private val inflateMovieDetails: (MovieDetailsDemo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val movieDetailsHelper: MovieDetailsHelper = MovieDetailsHelperImpl()

        fun bind(data: MovieDetailsDemo) = with(binding) {
            inflateMovieDetails.invoke(data)
            movieDetailsHelper.setImage(ivSelectMovieCover, data.posterUrl)
            movieDetailsHelper.addGenreList(fblGenreList, data.genres, root.context)
            movieDetailsHelper.setRateText(tvMarkValue, data.ratingKinopoisk, root.context)
            onItemsClicklisteners()
            tvFilmTitle.text = data.movieName
            tvOriginalTitle.text = data.alternativeName ?: data.englishName ?: ""
            tvYearCountryRuntime.text = movieDetailsHelper.buildStringYearCountriesRuntime(
                data,
                binding.root.context
            )
        }

        private fun onItemsClicklisteners() = with(binding) {
            ivLike.setOnClickListener {
                swipeRight.invoke()
                notifyDataSetChanged()
            }
            ivSkip.setOnClickListener {
                swipeLeft.invoke()
                notifyDataSetChanged()
            }
            ivRevert.setOnClickListener {
                revert.invoke()
                notifyDataSetChanged()
            }
        }
    }

    private val datalist = arrayListOf<MovieDetailsDemo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSwipeableMovieCardBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return MovieCardVH(
            binding,
            swipeLeft,
            swipeRight,
            revert,
            inflateMovieDetails
        )
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: MovieCardVH, position: Int) {
        holder.bind(datalist[position])
    }


    fun setData(list: List<MovieDetailsDemo>) {
        datalist.clear()
        datalist.addAll(list)
        notifyItemChanged(0)
    }

    private companion object {
        const val GOOD_RATE_7 = 7.0f
        const val DOT_DELIMETER = " ∙ "
        const val MULTIPOINT = "..."
        const val MAX_COUNTRY_NUMBER = 3
    }
}