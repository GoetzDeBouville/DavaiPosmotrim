package com.davay.android.feature.waitsession.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.waitsession.presentation.WaitSessionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WaitSessionFragmentModule {

    @IntoMap
    @ViewModelKey(WaitSessionViewModel::class)
    @Binds
    fun bindVM(impl: WaitSessionViewModel): ViewModel
}