package com.davay.android.feature.match.presentation

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.davay.android.R
import com.davay.android.databinding.FragmentMatchBottomSheetBinding
import com.davay.android.extensions.formatMovieDuration
import com.davay.android.feature.selectmovie.MovieDetailsDemo
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MatchBottomSheetFragment(
    private val movieDetails: MovieDetailsDemo
) : BottomSheetDialogFragment() {
    private var _binding: FragmentMatchBottomSheetBinding? = null
    private val binding: FragmentMatchBottomSheetBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext(), theme) {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
                bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        fillData(movieDetails)
    }


    private fun initViews() {
        buildBottomSheet()
        hideUnusedItems()
        binding.matchProgressBtn.opvProgress.setProgressWithAnimation {
            Toast.makeText(requireContext(), "Finished", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun subscribe() {
        binding.matchProgressBtn.root.setOnClickListener {
            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun buildBottomSheet() {
        dialog?.setOnShowListener { dialog ->
            val bottomSheet =
                (dialog as BottomSheetDialog)
                    .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    private fun hideUnusedItems() {
        binding.matchMovieCard.apply {
            listOf(ivLike, ivSkip, ivRevert).forEach {
                it.visibility = View.GONE
            }
        }
    }

    private fun fillData(data: MovieDetailsDemo) {
        binding.matchMovieCard.apply {
            setImage(ivSelectMovieCover, data.posterUrl)
            addGenreList(fblGenreList, data.genres)
            tvFilmTitle.text = data.movieName
            tvOriginalTitle.text = data.alternativeName ?: data.englishName ?: ""
            tvYearCountryRuntime.text = buildStringYearCountriesRuntime(data)
            setRateText(tvMarkValue, data.ratingKinopoisk)
        }
    }

    private fun setImage(img: ImageView, url: String?) {
        img.load(url) {
            placeholder(com.davai.uikit.R.drawable.placeholder_general_80)
                .scale(Scale.FIT)
            error(R.drawable.ic_movie_selection_error_332)
                .scale(Scale.FIT)
            transformations(RoundedCornersTransformation())
                .crossfade(true)
        }
    }

    private fun setRateText(tvRate: TextView, ratingKinopoisk: Float?) {
        ratingKinopoisk?.let {
            val textColor = if (it >= GOOD_RATE_7) {
                resources.getColor(com.davai.uikit.R.color.done, null)
            } else {
                resources.getColor(com.davai.uikit.R.color.attention, null)
            }
            tvRate.apply {
                text = it.toString()
                setTextColor(textColor)
            }
        }
    }

    private fun addGenreList(flexBox: FlexboxLayout, genres: List<String>) = with(binding) {
        flexBox.removeAllViews()
        genres.forEach {
            val genreView = LayoutInflater.from(root.context)
                .inflate(
                    R.layout.item_genre,
                    flexBox,
                    false
                ) as TextView
            genreView.text = it
            flexBox.addView(genreView)
        }
    }

    private fun buildStringYearCountriesRuntime(data: MovieDetailsDemo): String {
        with(data) {
            val str = StringBuilder()
            year?.let {
                str.append(it)
                str.append(DOT_DELIMETER)
            }
            if (countries.isNotEmpty()) {
                val countryList = countries.take(MAX_COUNTRY_NUMBER)
                val countriesString =
                    countryList.joinToString(separator = DOT_DELIMETER)
                str.append(countriesString)
                if (countries.size > MAX_COUNTRY_NUMBER) {
                    str.append(MULTIPOINT)
                }
            }
            movieLengthMin?.let {
                str.append(DOT_DELIMETER)
                str.append(movieLengthMin.formatMovieDuration(requireContext()))
            }
            return str.toString()
        }
    }

    companion object {
        const val MATCH_KEY = "match_key"
        const val DOT_DELIMETER = " ∙ "
        const val MULTIPOINT = "..."
        const val MAX_COUNTRY_NUMBER = 3
        const val GOOD_RATE_7 = 7.0f
    }
}