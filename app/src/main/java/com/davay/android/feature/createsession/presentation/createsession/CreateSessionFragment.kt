package com.davay.android.feature.createsession.presentation.createsession

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.davai.uikit.BannerView
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.app.MainActivity
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCreateSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
import com.davay.android.feature.createsession.presentation.compilations.CompilationsFragment
import com.davay.android.feature.createsession.presentation.genre.GenreFragment
import com.google.android.material.tabs.TabLayoutMediator

class CreateSessionFragment : BaseFragment<FragmentCreateSessionBinding, CreateSessionViewModel>(
    FragmentCreateSessionBinding::inflate
) {
    override val viewModel: CreateSessionViewModel by injectViewModel<CreateSessionViewModel>()
    private var tabMediator: TabLayoutMediator? = null

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
        setupToolbar()
        binding.btnContinue.setOnClickListener {
            val fragmentPosition = binding.viewPager.currentItem
            when (val fragment = childFragmentManager.findFragmentByTag("f$fragmentPosition")) {
                is CompilationsFragment -> {
                    fragment.viewModel.buttonContinueClicked()
                }

                is GenreFragment -> {
                    fragment.viewModel.buttonContinueClicked()
                }
            }
        }
        updateBanner(
            getString(R.string.create_session_choose_compilations_one),
            BannerView.ATTENTION
        )
    }

    private fun initTabs() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        tabMediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.create_session_compilations)
                1 -> tab.text = getString(R.string.create_session_genre)
            }
        }
        tabMediator?.attach()
    }

    private fun setupToolbar() {
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding.toolBar.setSubtitleText(getString(R.string.create_session_choose_compilations))
                        updateBanner(
                            getString(R.string.create_session_choose_compilations_one),
                            BannerView.ATTENTION
                        )
                    }

                    1 -> {
                        binding.toolBar.setSubtitleText(getString(R.string.create_session_choose_genre))
                        updateBanner(
                            getString(R.string.create_session_choose_genre_one),
                            BannerView.ATTENTION
                        )
                    }
                }
            }
        })
        with(binding.toolBar) {
            setStartIcon(com.davai.uikit.R.drawable.ic_arrow_back)
            showStartIcon()
            setStartIconClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun updateBanner(text: String, type: Int) {
        (requireActivity() as MainActivity).updateBanner(text, type)
    }

    override fun onDestroyView() {
        tabMediator?.detach()
        tabMediator = null
        super.onDestroyView()
    }
}
