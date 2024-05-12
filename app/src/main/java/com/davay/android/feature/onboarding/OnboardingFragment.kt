package com.davay.android.feature.onboarding

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentOnboardingBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.load.di.DaggerLoadFragmentComponent

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding,
        OnboardingViewModel>(FragmentOnboardingBinding::inflate) {
    override val viewModel: OnboardingViewModel by injectViewModel<OnboardingViewModel>()

    override fun diComponent(): ScreenComponent = DaggerLoadFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()
}