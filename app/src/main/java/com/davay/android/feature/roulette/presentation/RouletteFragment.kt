package com.davay.android.feature.roulette.presentation

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentRouletteBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.roulette.di.DaggerRouletteFragmentComponent

class RouletteFragment :
    BaseFragment<FragmentRouletteBinding, RouletteViewModel>(FragmentRouletteBinding::inflate) {

    override val viewModel: RouletteViewModel by injectViewModel<RouletteViewModel>()

    override fun diComponent(): ScreenComponent = DaggerRouletteFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()
}
