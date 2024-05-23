package com.davay.android.feature.moviedetails.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.moviedetails.presentation.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface MovieDetailsFragmentModule {
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    @Binds
    fun bindVM(impl: MovieDetailsViewModel): ViewModel
}