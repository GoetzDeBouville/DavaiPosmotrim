package com.davay.android.feature.selectmovie.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.davay.android.R
import com.davay.android.databinding.ItemSwipeableMovieCardBinding
import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo

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
        init {
            onItemsClicklisteners()
        }

        fun bind(data: MovieDetailsDemo) = with(binding) {
            inflateMovieDetails.invoke(data)
            bindImage(data)
            addGenreList(data.genres)
            setRateText(data.ratingKinopoisk)
            tvFilmTitle.text = data.movieName
            tvOriginalTitle.text = data.alternativeName ?: data.englishName ?: ""
            tvYearCountryRuntime.text = buildStringYearCountriesRuntime(
                data,
                binding.root.context
            )
        }

        private fun bindImage(data: MovieDetailsDemo) {
            binding.ivSelectMovieCover.load(data.posterUrl) {
                placeholder(com.davai.uikit.R.drawable.placeholder_general_80)
                    .scale(Scale.FIT)
                error(R.drawable.ic_movie_selection_error_332)
                    .scale(Scale.FIT)
                transformations(RoundedCornersTransformation())
                    .crossfade(true)
            }
        }

        private fun setRateText(ratingKinopoisk: Float?) = with(binding) {
            ratingKinopoisk?.let {
                val textColor = if (it >= GOOD_RATE_7) {
                    root.context.getColor(com.davai.uikit.R.color.done)
                } else {
                    root.context.getColor(com.davai.uikit.R.color.attention)
                }
                tvMarkValue.apply {
                    text = it.toString()
                    setTextColor(textColor)
                }
            }
        }

        private fun onItemsClicklisteners() = with(binding) {
            civLike.setOnClickListener {
                swipeRight.invoke()
            }
            civSkip.setOnClickListener {
                swipeLeft.invoke()
            }
            civRevert.setOnClickListener {
                revert.invoke()
            }
        }

        private fun buildStringYearCountriesRuntime(
            data: MovieDetailsDemo,
            context: Context
        ): String {
            with(data) {
                val str = StringBuilder()
                year?.let {
                    str.append(it)
                    str.append(DOT_DELIMETER)
                }
                if (countries.isNotEmpty()) {
                    val countryList = countries.take(MAX_COUNTRY_NUMBER_3)
                    val countriesString = countryList.joinToString(separator = DOT_DELIMETER)
                    str.append(countriesString)
                    if (countries.size > MAX_COUNTRY_NUMBER_3) {
                        str.append(MULTIPOINT)
                    }
                }
                movieLengthMin?.let {
                    str.append(DOT_DELIMETER)
                    str.append(formatMovieDuration(movieLengthMin, context))
                }
                return str.toString()
            }
        }

        private fun formatMovieDuration(minutes: Int, context: Context): String {
            val hours = minutes / MINUTES_NUMBER_IN_HOUR
            val remainingMinutes = minutes % MINUTES_NUMBER_IN_HOUR

            return when {
                hours > 0 && remainingMinutes > 0 -> context.getString(
                    R.string.select_movies_hours_and_minutes,
                    hours,
                    remainingMinutes
                )

                hours > 0 -> context.getString(R.string.select_movies_hours, hours)
                else -> context.getString(R.string.select_movies_minutes, remainingMinutes)
            }
        }

        private fun addGenreList(genres: List<String>) = with(binding) {
            fblGenreList.removeAllViews()
            genres.forEach {
                val genreView = LayoutInflater.from(root.context)
                    .inflate(
                        R.layout.item_genre,
                        fblGenreList,
                        false
                    ) as TextView
                genreView.text = it
                fblGenreList.addView(genreView)
            }
        }

        fun updateSwipeTransition(dx: Float) = with(binding) {
            civLike.updateDynamicAlphaPositive(dx)
            civSkip.updateDynamicAlphaNegative(dx)
        }
    }

    private val datalist = mutableListOf<MovieDetailsDemo>()

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
    }

    private companion object {
        const val GOOD_RATE_7 = 7.0f
        const val DOT_DELIMETER = " ∙ "
        const val MULTIPOINT = "..."
        const val MAX_COUNTRY_NUMBER_3 = 3
        const val MINUTES_NUMBER_IN_HOUR = 60
    }
}