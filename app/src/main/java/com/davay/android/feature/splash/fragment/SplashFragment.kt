package com.davay.android.feature.splash.fragment

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSplashBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.splash.di.DaggerSplashFragmentComponent
import com.davay.android.feature.splash.viewmodel.SplashViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
), SensorEventListener2 {
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
}
