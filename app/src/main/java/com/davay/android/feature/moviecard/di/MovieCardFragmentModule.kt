package com.davay.android.feature.moviecard.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.moviecard.presentation.MovieCardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MovieCardFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieCardViewModel::class)
    fun bindVM(impl: MovieCardViewModel): ViewModel
}