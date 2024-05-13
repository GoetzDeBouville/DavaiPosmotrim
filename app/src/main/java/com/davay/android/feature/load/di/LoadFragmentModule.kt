package com.davay.android.feature.load.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.load.presentation.LoadViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoadFragmentModule {

    @IntoMap
    @ViewModelKey(LoadViewModel::class)
    @Binds
    fun bindVM(impl: LoadViewModel): ViewModel
}
