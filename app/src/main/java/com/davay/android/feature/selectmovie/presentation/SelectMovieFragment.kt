package com.davay.android.feature.selectmovie.presentation

import android.os.Bundle
import android.view.View
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSelectMovieBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.selectmovie.di.DaggerSelectMovieFragmentComponent

class SelectMovieFragment :
    BaseFragment<FragmentSelectMovieBinding, SelectMovieViewModel>(FragmentSelectMovieBinding::inflate) {

    override val viewModel: SelectMovieViewModel by injectViewModel<SelectMovieViewModel>()
    private var matchesCounter = 0 // TODO заменит на подписку

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
    }

    private fun setDefaultToolbar() {
        binding.toolbar.apply {
            setStartIcon(com.davai.uikit.R.drawable.ic_cross)
            setEndIcon(com.davai.uikit.R.drawable.ic_heart)
            showEndIcon()
            setTitleText(requireContext().getString(R.string.select_movies_select_film))
            updateMatchesDisplay(matchesCounter)
        }
    }
}