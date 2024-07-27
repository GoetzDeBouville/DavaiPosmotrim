package com.davay.android.feature.onboarding.di

import com.davay.android.di.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [OnboardingFragmentModule::class]
)
interface OnBoardingFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): OnBoardingFragmentComponent
    }
}
