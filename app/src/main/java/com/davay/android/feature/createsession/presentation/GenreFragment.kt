package com.davay.android.feature.createsession.presentation

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentGenreBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent

class GenreFragment : BaseFragment<FragmentGenreBinding, GenreViewModel>(
    FragmentGenreBinding::inflate
) {
    override val viewModel: GenreViewModel by injectViewModel<GenreViewModel>()

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()
}
