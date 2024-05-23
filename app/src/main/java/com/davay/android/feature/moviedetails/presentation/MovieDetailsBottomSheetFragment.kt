package com.davay.android.feature.moviedetails.presentation

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseBottomSheetFragment
import com.davay.android.databinding.FragmentMovieDetailsBottomSheetBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.moviedetails.di.DaggerMovieDetailsFragmentComponent

class MovieDetailsBottomSheetFragment :
    BaseBottomSheetFragment<FragmentMovieDetailsBottomSheetBinding, MovieDetailsViewModel>(
        FragmentMovieDetailsBottomSheetBinding::inflate
    ) {
    override val viewModel: MovieDetailsViewModel by injectViewModel<MovieDetailsViewModel>()
    override fun diComponent(): ScreenComponent = DaggerMovieDetailsFragmentComponent
        .builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()
}