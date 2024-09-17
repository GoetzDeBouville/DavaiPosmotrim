package com.davay.android.feature.createsession.presentation.createsession

import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.davai.util.setOnDebouncedClickListener
import com.davay.android.R
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCreateSessionBinding
import com.davay.android.di.AppComponentHolder
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
import com.davay.android.feature.createsession.presentation.compilations.CompilationsFragment
import com.davay.android.feature.createsession.presentation.createsession.adapter.ViewPagerAdapter
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

    override fun initViews() {
        super.initViews()
        initTabs()
        setupToolbar()
    }

    override fun subscribe() {
        super.subscribe()
        binding.btnContinue.setOnDebouncedClickListener(
            coroutineScope = lifecycleScope
        ) {
            val fragmentPosition = binding.viewPager.currentItem
            when (val fragment = childFragmentManager.findFragmentByTag("f$fragmentPosition")) {
                is CompilationsFragment -> fragment.buttonContinueClicked()
                is GenreFragment -> fragment.buttonContinueClicked()
            }
        }
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

    private fun setupToolbar() = with(binding) {
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                resetSelections()
                when (position) {
                    0 -> toolBar.setSubtitleText(getString(R.string.create_session_choose_compilations))
                    1 -> toolBar.setSubtitleText(getString(R.string.create_session_choose_genre))
                }
            }
        })

        toolBar.setStartIconClickListener {
            viewModel.navigateBack()
        }
    }

    override fun onDestroyView() {
        tabMediator?.detach()
        tabMediator = null
        super.onDestroyView()
    }

    private fun resetSelections() {
        val genreFragment = childFragmentManager.findFragmentByTag("f1") as? GenreFragment
        val compilationsFragment =
            childFragmentManager.findFragmentByTag("f0") as? CompilationsFragment

        genreFragment?.resetSelections()
        compilationsFragment?.resetSelections()
    }
}
