package com.davay.android.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davai.uikit.extensions.applyBlurEffect
import com.davai.uikit.extensions.clearBlurEffect
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import com.davay.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
//        val navController = navHostFragment.navController

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setMarginBanner()
    }

    /**
     * Добавляет отступ снизу для клавиатуры
     */
    fun setKeyBoardInsets(
        view: View,
        left: Int = 0,
        top: Int = 0,
        right: Int = 0,
        bottom: Int = 0
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            if (insets.isVisible(WindowInsetsCompat.Type.ime())) {
                v.setPadding(left, top, right, bottom + imeHeight)
            } else {
                v.setPadding(left, top, right, bottom)
            }
            insets
        }
    }

    private fun setMarginBanner() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.banner) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    fun updateBanner(text: String, type: Int) {
        binding.banner.apply {
            setBannerText(text)
            setState(type)
        }
    }

    fun showBanner() {
        val mediumAnimationDuration = resources.getInteger(android.R.integer.config_mediumAnimTime)
        binding.banner.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(mediumAnimationDuration.toLong())
                .setListener(null)
        }
        with(binding.banner) {
            postDelayed(BANNER_TIME_DELAY_2000_MS) {
                animate()
                    .alpha(0f)
                    .setDuration(mediumAnimationDuration.toLong())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.banner.visibility = View.GONE
                        }
                    })
            }
        }
    }

    fun applyBlurEffect() {
        binding.root.applyBlurEffect()
    }

    fun clearBlurEffect() {
        binding.root.clearBlurEffect()
    }

    companion object {
        const val BANNER_TIME_DELAY_2000_MS = 2000L
    }
}

