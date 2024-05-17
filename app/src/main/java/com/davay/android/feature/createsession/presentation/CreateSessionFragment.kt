package com.davay.android.feature.createsession.presentation

import android.os.Bundle
import android.view.View
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCreateSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
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
    }

    private fun initTabs(){
        binding.viewPager.adapter = ViewPagerAdapter(this)
        tabMediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.create_session_compilations)
                1 -> tab.text = getString(R.string.create_session_genre)
            }
        }
        tabMediator?.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator?.detach()
        tabMediator = null
    }
}
