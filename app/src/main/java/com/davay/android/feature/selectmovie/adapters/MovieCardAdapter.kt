package com.davay.android.feature.selectmovie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.davay.android.R
import com.davay.android.databinding.ItemSwipeableMovieCardBinding
import com.davay.android.feature.selectmovie.MovieDetailsDemo

class MovieCardAdapter : RecyclerView.Adapter<MovieCardAdapter.MovieCardVH>() {
    inner class MovieCardVH(private val binding: ItemSwipeableMovieCardBinding) :
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

    private fun buildStringYearCountriesRuntime(data: MovieDetailsDemo, context: Context): String {
        with(data) {
            val str = StringBuilder()
            year?.let {
                str.append(it)
            }
            if (countries.isNotEmpty()) {
                val countryList = countries.subList(0, 2)
                val countriesString = countryList.joinToString {
                    COMMA_DELIMETER
                }
                str.append(DOT_DELIMETER)
                str.append(countriesString)
                if (countryList.size > MAX_COUNTRY_NUMBER) {
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

    private companion object {
        const val GOOD_RATE_7 = 7.0f
        const val DOT_DELIMETER = " ∙ "
        const val COMMA_DELIMETER = " ,"
        const val MULTIPOINT = "..."
        const val MAX_COUNTRY_NUMBER = 3
        const val MINUTES_NUMBER_IN_HOUR = 60
    }
}