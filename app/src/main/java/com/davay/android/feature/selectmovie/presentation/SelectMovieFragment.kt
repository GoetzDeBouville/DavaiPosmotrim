package com.davay.android.feature.selectmovie.presentation

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSelectMovieBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.selectmovie.di.DaggerSelectMovieFragmentComponent

class SelectMovieFragment :
    BaseFragment<FragmentSelectMovieBinding, SelectMovieViewModel>(FragmentSelectMovieBinding::inflate) {

    override val viewModel: SelectMovieViewModel by injectViewModel<SelectMovieViewModel>()
    override fun diComponent(): ScreenComponent = DaggerSelectMovieFragmentComponent
        .builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()
}