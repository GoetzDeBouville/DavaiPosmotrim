package com.davay.android.feature.selectmovie.presentation

import android.os.Bundle
import android.view.View
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSelectMovieBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.extensions.dpToPx
import com.davay.android.feature.selectmovie.di.DaggerSelectMovieFragmentComponent
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SelectMovieFragment :
    BaseFragment<FragmentSelectMovieBinding, SelectMovieViewModel>(FragmentSelectMovieBinding::inflate) {

    override val viewModel: SelectMovieViewModel by injectViewModel<SelectMovieViewModel>()
    private var matchesCounter = 0 // заменить на подписку

    override fun diComponent(): ScreenComponent = DaggerSelectMovieFragmentComponent
        .builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setToolbar()
        setBottomSheet()
    }

    private fun setBottomSheet() = with(binding) {
        BottomSheetBehavior.from(clDetailsBottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = BOTTOMSHEET_PEEK_HEIGHT_112_DP.dpToPx().toInt()

            clDetailsBottomSheet.post {
                val cardLocation = IntArray(2)
                mcvFilmCard.getLocationOnScreen(cardLocation)
                val cardTop = cardLocation[1]

                val screenHeight = resources.displayMetrics.heightPixels
                val maxHeight = screenHeight - cardTop

                clDetailsBottomSheet.layoutParams.height = maxHeight
                clDetailsBottomSheet.requestLayout()
            }
        }
    }

    private fun setToolbar() {
        binding.toolbarviewHeader.apply {
            setStartIcon(com.davai.uikit.R.drawable.ic_cross)
            setEndIcon(com.davai.uikit.R.drawable.ic_heart)
            showEndIcon()
            setTitleText(requireContext().getString(R.string.select_movies_select_film))
            updateMatchesDisplay(matchesCounter)
            addStatusBarSpacer()
        }
    }

    private companion object {
        const val BOTTOMSHEET_PEEK_HEIGHT_112_DP = 112
    }
}
