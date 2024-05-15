package com.davay.android.feature.splash.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSplashBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.splash.di.DaggerSplashFragmentComponent
import com.davay.android.feature.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {
    override val viewModel: SplashViewModel by injectViewModel<SplashViewModel>()
    override fun diComponent(): ScreenComponent = DaggerSplashFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tv = binding.textview10
        val bottomTv = binding.tvBottomItem

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels

        val finalY = screenHeight / 2 - (tv.height + bottomTv.y)

        binding.root.children.filterIsInstance<TextView>().forEachIndexed { index, textView ->
            lifecycleScope.launch {
                delay(index * 100L)
                ObjectAnimator.ofFloat(textView, "translationY", finalY).apply {
                    duration = 300
                    start()
                }
            }
        }
    }
}
