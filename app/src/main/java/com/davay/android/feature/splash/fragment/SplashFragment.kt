package com.davay.android.feature.splash.fragment

import android.animation.ObjectAnimator
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
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
), SensorEventListener2 {
    override val viewModel: SplashViewModel by injectViewModel<SplashViewModel>()
    private val sensorManager: SensorManager by lazy {
        requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
    }
    private var isAnimationFinished = false

    override fun diComponent(): ScreenComponent = DaggerSplashFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateApearenceOfElements()
        getSensorAccelerometer()
    }

    private fun getSensorAccelerometer() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    private fun animateApearenceOfElements() {
        val tv = binding.textview10
        val bottomTv = binding.tvBottomItem

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels

        val finalY = screenHeight / 2 - (tv.height + bottomTv.y)

        binding.root.children.filterIsInstance<TextView>().forEachIndexed { index, textView ->
            lifecycleScope.launch {
                delay(index * DELAY_100_MS)
                ObjectAnimator.ofFloat(textView, "translationY", finalY).apply {
                    duration = DURATION_300_MS
                    start()
                }
            }
        }

        lifecycleScope.launch {
            delay(DELAY_100_MS * 13)
            isAnimationFinished = true
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (isAnimationFinished) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                animateTranslation(event)
            }
        }
    }

    private fun animateTranslation(event: SensorEvent) {
        val leftRight = event.values[0]
        val upDown = event.values[1]

        binding.root.children.forEach {
            if (it is TextView) {
                val newX = it.translationX + leftRight * -5
                val newY = it.translationY + upDown * 5

                if (newX >= binding.linearLayout.left && newX + it.width <= binding.linearLayout.right) {
                    it.translationX = newX
                }
                if (newY >= binding.linearLayout.top && newY + it.height <= binding.linearLayout.bottom) {
                    it.translationY = newY
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onFlushCompleted(sensor: Sensor?) = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        sensorManager.unregisterListener(this)
    }

    companion object {
        const val DELAY_100_MS = 100L
        const val DURATION_300_MS = 200L
    }
}
