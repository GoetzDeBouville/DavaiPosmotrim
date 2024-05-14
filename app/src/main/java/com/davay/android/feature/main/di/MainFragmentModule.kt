package com.davay.android.feature.main.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.main.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainFragmentModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindVM(impl: MainViewModel): ViewModel
}
