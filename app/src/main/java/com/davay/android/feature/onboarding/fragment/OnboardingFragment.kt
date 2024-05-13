package com.davay.android.feature.onboarding.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
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
    private val fragmentList = listOf(
        OnboardingFirstFragment(),
        OnboardingSecondFragment(),
        OnboardingThirdFragment()
    )
    private val viewPagerAdapter by lazy {
        OnboardingViewPagerAdapter(
            fragmentList,
            viewLifecycleOwner.lifecycle,
            this.childFragmentManager
        )
    }

    override fun diComponent(): ScreenComponent = DaggerOnBoardingFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setUpViewPager()
        setUpButtonClickListener()
    }

    private fun initViews() = with(binding) {
        viewpager.adapter = viewPagerAdapter
        ciIndicator.setViewPager(viewpager)
    }

    private fun setUpViewPager() {
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonTextForFragment(position)
            }
        })
    }

    private fun updateButtonTextForFragment(position: Int) {
        binding.mbtnFooterBtn.text = when (position) {
            fragmentList.lastIndex -> resources.getString(com.davai.uikit.R.string.begin)
            else -> resources.getString(com.davai.uikit.R.string.continue_view)
        }
    }

    private fun setUpButtonClickListener() {
        binding.mbtnFooterBtn.setOnClickListener {
            val currentFragment = binding.viewpager.currentItem
            val nextFragment = currentFragment + 1
            if (nextFragment < fragmentList.size) {
                binding.viewpager.setCurrentItem(nextFragment, true)
            } else {
                Toast.makeText(
                    requireContext(),
                    "переход на экран регистрации",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
}