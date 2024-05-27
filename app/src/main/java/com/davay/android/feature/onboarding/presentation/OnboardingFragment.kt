package com.davay.android.feature.onboarding.presentation

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.davay.android.R
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

    private var listarrayid = listOfIds
    private val fragmentList = listOf(
        OnboardingFirstFragment(listarrayid[0]),
        OnboardingFirstFragment(listarrayid[1]),
        OnboardingFirstFragment(listarrayid[2])
    )

    override fun diComponent(): ScreenComponent = DaggerOnBoardingFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        setUpButtonClickListener()
        initViews()
    }

    private fun initViews() = with(binding) {
        viewpager.adapter = OnboardingViewPagerAdapter(
            fragmentList,
            viewLifecycleOwner.lifecycle,
            this@OnboardingFragment.childFragmentManager
        )
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
            fragmentList.lastIndex -> resources.getString(R.string.onboarding_begin)
            else -> resources.getString(R.string.onboarding_continue_view)
        }
    }

    private fun setUpButtonClickListener() {
        binding.mbtnFooterBtn.setOnClickListener {
            val currentFragment = binding.viewpager.currentItem
            val nextFragment = currentFragment + 1
            if (nextFragment < fragmentList.size) {
                binding.viewpager.setCurrentItem(nextFragment, true)
            } else {
                viewModel.navigate(listarrayid[3][0])
            }
        }
    }

    companion object {
        const val ONBOARDING_KEY = "onboarding_key"
        const val ONBOARDING_MAIN_SET = 0
        const val ONBOARDING_INSTRUCTION_SET = 1

        private val listOfIds = arrayOf(
            intArrayOf(
                R.string.onboarding_choose_full_text,
                R.drawable.img_search_movies,
                R.string.onboarding_with_no_more_dicussions
            ),
            intArrayOf(
                R.string.onboarding_for_large_company,
                R.drawable.img_large_party,
                R.string.onboarding_choose_movies_with_any
            ),
            intArrayOf(
                R.string.onboarding_movie_library,
                R.drawable.img_favorite_film,
                R.string.onboarding_only_favorite_genres
            ),
            intArrayOf(
                R.id.action_onboardingFragment_to_mainFragment
            )
        )

        private val listOfIds2 = arrayOf(
            intArrayOf(
                R.string.onboarding_choose_full_text,
                R.drawable.img_search_movies,
                R.string.onboarding_with_no_more_dicussions
            ),
            intArrayOf(
                R.string.onboarding_for_large_company,
                R.drawable.img_large_party,
                R.string.onboarding_choose_movies_with_any
            ),
            intArrayOf(
                R.string.onboarding_movie_library,
                R.drawable.img_favorite_film,
                R.string.onboarding_only_favorite_genres
            ),
            intArrayOf(R.id.action_onboardingFragment_to_mainFragment)
        )
    }
}