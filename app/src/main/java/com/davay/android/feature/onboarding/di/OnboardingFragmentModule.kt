package com.davay.android.feature.onboarding.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.onboarding.viewmodel.OnboardingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OnboardingFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    fun bindVM(impl: OnboardingViewModel): ViewModel
}
