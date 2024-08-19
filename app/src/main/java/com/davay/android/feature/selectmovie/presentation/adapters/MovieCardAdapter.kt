package com.davay.android.feature.selectmovie.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.databinding.ItemSwipeableMovieCardBinding
import com.davay.android.utils.MovieDetailsHelper
import com.davay.android.utils.MovieDetailsHelperImpl
import com.davay.android.utils.setOnDebouncedClickListener
import kotlinx.coroutines.CoroutineScope

class MovieCardAdapter(
    private val coroutineScope: CoroutineScope,
    private val swipeLeft: () -> Unit,
    private val swipeRight: () -> Unit,
    private val revert: () -> Unit,
    private val inflateMovieDetails: (MovieDetails) -> Unit
) : RecyclerView.Adapter<MovieCardAdapter.MovieCardVH>() {
    inner class MovieCardVH(
        private val binding: ItemSwipeableMovieCardBinding,
        private val swipeLeft: () -> Unit,
        private val swipeRight: () -> Unit,
        private val revert: () -> Unit,
        private val inflateMovieDetails: (MovieDetails) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            onItemsClickListeners()
        }

        private val movieDetailsHelper: MovieDetailsHelper = MovieDetailsHelperImpl()

        fun bind(data: MovieDetails) = with(binding) {
            inflateMovieDetails.invoke(data)
            movieDetailsHelper.setImage(ivSelectMovieCover, progressBar, data.imgUrl)
            movieDetailsHelper.addGenreList(fblGenreList, data.genres)
            movieDetailsHelper.setRateText(tvMarkValue, data.ratingKinopoisk)
            tvFilmTitle.text = data.name
            tvOriginalTitle.text = data.alternativeName ?: ""
            tvYearCountryRuntime.text = movieDetailsHelper.buildStringYearCountriesRuntime(
                data,
                binding.root.context
            )
        }

        fun updateSwipeTransition(dx: Float) = with(binding) {
            civLike.updateDynamicAlphaPositive(dx)
            civSkip.updateDynamicAlphaNegative(dx)
        }

        private fun onItemsClickListeners() = with(binding) {
            civLike.setOnDebouncedClickListener(
                coroutineScope = coroutineScope
            ) {
                swipeRight.invoke()
                notifyDataSetChanged()
            }
            civSkip.setOnDebouncedClickListener(
                coroutineScope = coroutineScope
            ) {
                swipeLeft.invoke()
                notifyDataSetChanged()
            }
            civRevert.setOnDebouncedClickListener(
                coroutineScope = coroutineScope
            ) {
                revert.invoke()
                notifyDataSetChanged()
            }
        }
    }

    private val datalist = arrayListOf<MovieDetails>()

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


    fun setData(list: List<MovieDetails>) {
        datalist.clear()
        datalist.addAll(list)
        notifyItemChanged(0)
    }
}