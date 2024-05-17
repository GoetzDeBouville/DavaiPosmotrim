package com.davay.android.feature.createsession.presentation

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCreateSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent

class CreateSessionFragment : BaseFragment<FragmentCreateSessionBinding, CreateSessionViewModel>(
    FragmentCreateSessionBinding::inflate
) {
    override val viewModel: CreateSessionViewModel by injectViewModel<CreateSessionViewModel>()

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()
}
