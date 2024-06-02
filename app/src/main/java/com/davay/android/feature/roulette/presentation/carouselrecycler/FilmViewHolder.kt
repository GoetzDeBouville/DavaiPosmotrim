package com.davay.android.feature.roulette.presentation.carouselrecycler

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.davay.android.databinding.ItemFilmBinding
import com.davay.android.feature.roulette.presentation.model.FilmRouletteModel
import kotlin.math.roundToInt

class FilmViewHolder(private val binding: ItemFilmBinding, parentWidth: Int) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.layoutParams = RecyclerView.LayoutParams(
            (parentWidth * 0.75f).roundToInt(),
            RecyclerView.LayoutParams.MATCH_PARENT
        )
    }

    fun bind(film: FilmRouletteModel) {
        binding.tvFilmTitle.text = film.title
        binding.tvOriginalTitle.text = film.originalTitle
        binding.tvMarkValue.text = film.mark
        binding.tvYearCountryRuntime.text = film.yearCountryRuntime

        binding.ivSelectMovieCover.load(film.posterUrl) {
            placeholder(com.davai.uikit.R.drawable.placeholder_general_80)
                .scale(Scale.FIT)
            error(com.davai.uikit.R.drawable.placeholder_general_80)
                .scale(Scale.FIT)
            transformations(RoundedCornersTransformation())
                .crossfade(true)
        }
    }
}