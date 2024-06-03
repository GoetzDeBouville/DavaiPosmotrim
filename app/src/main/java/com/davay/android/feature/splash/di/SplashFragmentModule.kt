package com.davay.android.feature.splash.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.splash.viewmodel.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SplashFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun bindVM(impl: SplashViewModel): ViewModel
}
