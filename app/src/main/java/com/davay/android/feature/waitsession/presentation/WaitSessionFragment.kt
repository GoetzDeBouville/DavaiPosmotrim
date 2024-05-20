package com.davay.android.feature.waitsession.presentation

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentWaitSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.waitsession.di.DaggerWaitSessionFragmentComponent

class WaitSessionFragment : BaseFragment<FragmentWaitSessionBinding, WaitSessionViewModel>(
    FragmentWaitSessionBinding::inflate
) {
    override val viewModel: WaitSessionViewModel by injectViewModel<WaitSessionViewModel>()

    override fun diComponent(): ScreenComponent = DaggerWaitSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()
}