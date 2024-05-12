package com.davay.android.feature.onboarding.fragment

import android.os.Bundle
import android.view.View
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentOnboardingBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.onboarding.adapter.OnboardingViewPagerAdapter
import com.davay.android.feature.onboarding.di.DaggerOnBoardingFragmentComponent
import com.davay.android.feature.onboarding.viewmodel.OnboardingViewModel

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingViewModel>(
    FragmentOnboardingBinding::inflate
) {
    override val viewModel: OnboardingViewModel by injectViewModel<OnboardingViewModel>()

    override fun diComponent(): ScreenComponent = DaggerOnBoardingFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        val fragmentList = listOf(
            OnboardingFirstFragment(),
            OnboardingSecondFragment(),
            OnboardingThirdFragment()
        )

        viewpager.adapter = OnboardingViewPagerAdapter(
            fragmentList,
            viewLifecycleOwner.lifecycle,
            this@OnboardingFragment.childFragmentManager
        ) { position ->
            updateButtonText(position)
        }

        ciIndicator.setViewPager(viewpager)
    }

    private fun updateButtonText(position: Int) = with(binding) {
        mbtnFooterBtn.apply {
            if (position == SECOND_FRAGMENT_POSITION) {
                setText(com.davai.uikit.R.string.begin)
            } else {
                setText(com.davai.uikit.R.string.continue_view)
            }
        }
    }

    private companion object {
        const val SECOND_FRAGMENT_POSITION = 2
    }
}