package com.davay.android.feature.selectmovie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.davay.android.R
import com.davay.android.databinding.ItemSwipeableMovieCardBinding
import com.davay.android.feature.selectmovie.MovieDetailsDemo

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
        fun bind(data: MovieDetailsDemo) = with(binding) {
            inflateMovieDetails.invoke(data)
            bindImage(data)
            setRateText(data.ratingKinopoisk)
            onItemsClicklisteners()
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
                error(com.davai.uikit.R.drawable.placeholder_error_theme_112)
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
            ivLike.setOnClickListener {
                swipeRight.invoke()
            }
            ivSkip.setOnClickListener {
                swipeLeft.invoke()
            }
            ivRevert.setOnClickListener {
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
                    val countryList = countries.take(MAX_COUNTRY_NUMBER)
                    val countriesString = countryList.joinToString(separator = COMMA_DELIMETER)
                    str.append(countriesString)
                    if (countries.size > MAX_COUNTRY_NUMBER) {
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
                    R.string.select_movies_hours_and_minutes, hours, remainingMinutes
                )

                hours > 0 -> context.getString(R.string.select_movies_hours, hours)
                else -> context.getString(R.string.select_movies_minutes, remainingMinutes)
            }
        }
    }

    private val datalist = arrayListOf<MovieDetailsDemo>()
    private var currentPosition = 0

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
        currentPosition = holder.adapterPosition
    }

    fun updateMovieDetails() {
        notifyItemChanged(currentPosition)
    }

    fun setData(list: List<MovieDetailsDemo>) {
        datalist.clear()
        datalist.addAll(list)
        notifyItemChanged(0)
    }

    private companion object {
        const val GOOD_RATE_7 = 7.0f
        const val DOT_DELIMETER = " ∙ "
        const val COMMA_DELIMETER = ", "
        const val MULTIPOINT = "..."
        const val MAX_COUNTRY_NUMBER = 3
        const val MINUTES_NUMBER_IN_HOUR = 60
    }
}