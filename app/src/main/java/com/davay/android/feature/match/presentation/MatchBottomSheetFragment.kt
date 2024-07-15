package com.davay.android.feature.match.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import com.davai.extensions.dpToPx
import com.davay.android.databinding.FragmentMatchBottomSheetBinding
import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo
import com.davay.android.utils.MovieDetailsHelper
import com.davay.android.utils.MovieDetailsHelperImpl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MatchBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMatchBottomSheetBinding? = null
    private val binding: FragmentMatchBottomSheetBinding
        get() = _binding!!

    private val movieDetailsHelper: MovieDetailsHelper = MovieDetailsHelperImpl()
    private var movieDetails: MovieDetailsDemo? = null
    private var buttonText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieDetails =
                Gson().fromJson(it.getString(ARG_MOVIE_DETAILS), MovieDetailsDemo::class.java)
            buttonText = it.getString(ARG_BUTTON_TEXT)
        }
    }

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
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribe()
        movieDetails?.let { fillData(movieDetails!!) }
    }

    private fun initViews() {
        buildBottomSheet()
        hideUnusedItems()
        buttonText?.let { setButtonText(it) }
    }

    private fun subscribe() {
        binding.progressButtonItem.root.setOnClickListener {
            animateDialogDismiss()
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
            launchProgressButtonAnimation()
        }
    }

    private fun launchProgressButtonAnimation() {
        binding.progressButtonItem.progressButton.also {
            lifecycleScope.launch {
                it.animateProgress(this) {
                    animateDialogDismiss()
                }
            }
        }
    }

    private fun hideUnusedItems() {
        binding.matchMovieCard.apply {
            listOf(civLike, civSkip, civRevert).forEach {
                it.visibility = View.GONE
            }
        }
    }

    private fun fillData(data: MovieDetailsDemo) {
        binding.matchMovieCard.apply {
            movieDetailsHelper.setImage(ivSelectMovieCover, data.posterUrl)
            movieDetailsHelper.addGenreList(fblGenreList, data.genres)
            tvFilmTitle.text = data.movieName
            tvOriginalTitle.text = data.alternativeName ?: data.englishName ?: ""
            tvYearCountryRuntime.text =
                movieDetailsHelper.buildStringYearCountriesRuntime(data, requireContext())
            movieDetailsHelper.setRateText(tvMarkValue, data.ratingKinopoisk)
        }
    }

    private fun setButtonText(text: String) {
        binding.progressButtonItem.progressButton.text = text
    }

    private fun animateDialogDismiss() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
                ?: return

        val width = bottomSheet.width
        val height = bottomSheet.height

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels

        val translationX =
            screenWidth - width.toFloat() / 2 - bottomSheet.paddingEnd - EXTERNAL_SPACING_24_DP.dpToPx()
        val translationY = -height.toFloat() / 2 + EXTERNAL_SPACING_24_DP.dpToPx()

        val scaleX = ObjectAnimator.ofFloat(bottomSheet, View.SCALE_X, 0f)
        val scaleY = ObjectAnimator.ofFloat(bottomSheet, View.SCALE_Y, 0f)
        val translateX = ObjectAnimator.ofFloat(bottomSheet, View.TRANSLATION_X, translationX)
        val translateY = ObjectAnimator.ofFloat(bottomSheet, View.TRANSLATION_Y, translationY)
        val alpha = ObjectAnimator.ofFloat(bottomSheet, View.ALPHA, 0f)

        val animatorSet = AnimatorSet().apply {
            playTogether(scaleX, scaleY, translateX, translateY, alpha)
            duration = MORPHING_ANIMATION_DURATION_500_MS
        }

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dismiss()
            }
        })

        animatorSet.start()
    }

    companion object {
        private const val EXTERNAL_SPACING_24_DP = 24
        private const val MORPHING_ANIMATION_DURATION_500_MS = 500L
        private const val ARG_MOVIE_DETAILS = "movie_details"
        private const val ARG_BUTTON_TEXT = "button_text"

        fun newInstance(
            movieDetails: String,
            buttonText: String? = null
        ): MatchBottomSheetFragment {
            val fragment = MatchBottomSheetFragment()
            val args = Bundle().apply {
                putString(ARG_MOVIE_DETAILS, movieDetails)
                if (buttonText != null) putString(ARG_BUTTON_TEXT, buttonText)
            }
            fragment.arguments = args
            return fragment
        }
    }
}