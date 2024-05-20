package com.davay.android.feature.selectmovie.presentation

import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSelectMovieBinding
import com.davay.android.di.ScreenComponent

class SelectMovieFragment :
    BaseFragment<FragmentSelectMovieBinding, SelectMovieViewModel>(FragmentSelectMovieBinding::inflate) {
    override val viewModel: SelectMovieViewModel by injectViewModel<SelectMovieViewModel>()
    override fun diComponent(): ScreenComponent {
        TODO("Not yet implemented")
    }
}