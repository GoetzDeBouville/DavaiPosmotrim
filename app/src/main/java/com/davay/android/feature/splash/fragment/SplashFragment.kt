package com.davay.android.feature.splash.fragment

import android.content.Context.SENSOR_SERVICE
import android.content.res.ColorStateList
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSplashBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.splash.di.DaggerSplashFragmentComponent
import com.davay.android.feature.splash.viewmodel.SplashViewModel
import com.davay.android.utils.setSplashTextViewStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate),
    SensorEventListener2 {
    override val viewModel: SplashViewModel by injectViewModel<SplashViewModel>()
    private val sensorManager: SensorManager by lazy {
        requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun diComponent(): ScreenComponent = DaggerSplashFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSensorAccelerometer()
        lifecycleScope.launch {
            addTextViewsWithDelay()
        }
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

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val gravityX = -it.values[0]
            val gravityY = it.values[1]

            binding.physicsLayout.apply {
                physics.setGravity(gravityX, gravityY)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onFlushCompleted(sensor: Sensor?) = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        sensorManager.unregisterListener(this)
    }

    private suspend fun addTextViewsWithDelay() {
        val stringResIds = listOf(
            R.string.splash_comedy, R.string.splash_fantasy, R.string.splash_fantastic,
            R.string.splash_biopic, R.string.splash_action, R.string.splash_detective,
            R.string.splash_musical, R.string.splash_adventure, R.string.splash_watch,
            R.string.splash_melodrama, R.string.splash_thriller, R.string.splash_documental
        )

        for ((index, text) in stringResIds.shuffled().withIndex()) {
            delay(index * DELAY_10_MS)
            addTextViewToPhysicsLayout(text)
        }
    }

    private fun addTextViewToPhysicsLayout(textResId: Int) {
        val textView = TextView(context).apply {
            text = resources.getString(textResId)
            if (textResId == R.string.splash_watch) {
                backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        com.davai.uikit.R.color.primary_base
                    )
                )
            }
            setSplashTextViewStyle()
        }
        binding.physicsLayout.addView(textView)
    }

    companion object {
        const val DELAY_10_MS = 10L
        const val START_POSITIONS_NUMBER = 3
        const val MAX_ANGLE_3000 = 3000
        const val MAX_NEGATIVE_ANGLE_3000 = -3000
    }
}
