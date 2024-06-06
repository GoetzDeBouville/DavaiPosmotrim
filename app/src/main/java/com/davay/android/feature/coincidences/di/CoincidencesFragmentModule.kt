package com.davay.android.feature.coincidences.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.coincidences.data.TestMovieRepository
import com.davay.android.feature.coincidences.presentation.CoincidencesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CoincidencesFragmentModule {

    @IntoMap
    @ViewModelKey(CoincidencesViewModel::class)
    @Binds
    fun bindVM(impl: CoincidencesViewModel): ViewModel

}