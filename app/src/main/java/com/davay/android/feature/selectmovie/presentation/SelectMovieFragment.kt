package com.davay.android.feature.selectmovie.presentation

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
    private var matchesCounter = 0 // TODO заменить на подписку

    override fun diComponent(): ScreenComponent = DaggerSelectMovieFragmentComponent
        .builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setDefaultToolbar()
        setBottomSheet()
    }

    private fun setBottomSheet() {
        val bottomSheetContainer: BottomSheetBehavior<ConstraintLayout> =
            BottomSheetBehavior.from(binding.clDetailsBottomSheet).apply {
                state = BottomSheetBehavior.STATE_COLLAPSED
                peekHeight = 112.dpToPx().toInt()
            }
    }

    private fun setDefaultToolbar() {
        binding.toolbarviewHeader.apply {
            setStartIcon(com.davai.uikit.R.drawable.ic_cross)
            setEndIcon(com.davai.uikit.R.drawable.ic_heart)
            showEndIcon()
            setTitleText(requireContext().getString(R.string.select_movies_select_film))
            updateMatchesDisplay(matchesCounter)
            post {
                addStatusBarSpacer()
            }
        }
    }
}
