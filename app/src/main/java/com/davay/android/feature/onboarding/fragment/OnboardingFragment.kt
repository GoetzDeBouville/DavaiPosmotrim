package com.davay.android.feature.onboarding.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentOnboardingBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.load.di.DaggerLoadFragmentComponent
import com.davay.android.feature.onboarding.viewmodel.OnboardingViewModel

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding,
        OnboardingViewModel>(FragmentOnboardingBinding::inflate) {
    override val viewModel: OnboardingViewModel by injectViewModel<OnboardingViewModel>()

    override fun diComponent(): ScreenComponent = DaggerLoadFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    private fun initViews() = with(binding) {
//        viewpager.adapter =
    }
}