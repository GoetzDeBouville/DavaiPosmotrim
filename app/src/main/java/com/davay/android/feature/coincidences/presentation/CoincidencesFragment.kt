package com.davay.android.feature.coincidences.presentation

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCoincidencesBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.coincidences.di.DaggerCoincidencesFragmentComponent

class CoincidencesFragment : BaseFragment<FragmentCoincidencesBinding, CoincidencesViewModel>(FragmentCoincidencesBinding::inflate) {

    override val viewModel: CoincidencesViewModel by injectViewModel<CoincidencesViewModel>()
    override fun diComponent(): ScreenComponent = DaggerCoincidencesFragmentComponent
        .builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

}