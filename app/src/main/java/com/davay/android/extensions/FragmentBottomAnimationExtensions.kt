package com.davay.android.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

fun Fragment.animateBottom(
    listenableView: View,
    startBottomView: View,
    endBottomView: View,
    animateView: View
) {
    ViewCompat.setWindowInsetsAnimationCallback(
        listenableView,
        object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
            private var startBottom = 0f
            private var endBottom = 0f

            override fun onPrepare(
                animation: WindowInsetsAnimationCompat
            ) {
                startBottom = startBottomView.bottom.toFloat()
            }

            override fun onStart(
                animation: WindowInsetsAnimationCompat,
                bounds: WindowInsetsAnimationCompat.BoundsCompat
            ): WindowInsetsAnimationCompat.BoundsCompat {
                endBottom = endBottomView.bottom.toFloat()
                return bounds
            }

            override fun onProgress(
                insets: WindowInsetsCompat,
                runningAnimations: MutableList<WindowInsetsAnimationCompat>
            ): WindowInsetsCompat {
                val imeAnimation = runningAnimations.find {
                    it.typeMask and WindowInsetsCompat.Type.ime() != 0
                } ?: return insets
                animateView.translationY =
                    (startBottom - endBottom) * (1 - imeAnimation.interpolatedFraction)
                return insets
            }
        }
    )
}