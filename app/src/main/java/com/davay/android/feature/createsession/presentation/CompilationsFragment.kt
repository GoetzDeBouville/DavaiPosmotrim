package com.davay.android.feature.createsession.presentation

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCompilationsBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent

class CompilationsFragment : BaseFragment<FragmentCompilationsBinding, CompilationsViewModel>(
    FragmentCompilationsBinding::inflate
) {
    override val viewModel: CompilationsViewModel by injectViewModel<CompilationsViewModel>()

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    companion object {
        fun newInstance() = CompilationsFragment()
    }
}
