package com.davay.android.feature.splash.fragment

import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSplashBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.splash.di.DaggerSplashFragmentComponent
import com.davay.android.feature.splash.viewmodel.SplashViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {
    override val viewModel: SplashViewModel by injectViewModel<SplashViewModel>()
    override fun diComponent(): ScreenComponent = DaggerSplashFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()
}