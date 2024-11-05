package com.davay.android.feature.selectmovie.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.selectmovie.presentation.SelectMovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SelectMovieFragmentModule {
    @IntoMap
    @ViewModelKey(SelectMovieViewModel::class)
    @Binds
    fun bindVM(impl: SelectMovieViewModel): ViewModel
}