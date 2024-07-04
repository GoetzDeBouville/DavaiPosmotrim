package com.davay.android.feature.matchedsession.presentation

import android.os.Bundle
import android.view.View
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentMatchedSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.matchedsession.di.DaggerMatchedSessionFragmentComponent

class MatchedSessionFragment :
    BaseFragment<FragmentMatchedSessionBinding, MatchedSessionViewModel>(FragmentMatchedSessionBinding::inflate) {

    override val viewModel: MatchedSessionViewModel by injectViewModel<MatchedSessionViewModel>()

    override fun diComponent(): ScreenComponent = DaggerMatchedSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
