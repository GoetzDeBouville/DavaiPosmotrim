package com.davay.android.extensions

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View

/**
 * Методы управления blur effect
 */
fun View.applyBlurEffect(radius: Float = 15f, tileMode: Shader.TileMode = Shader.TileMode.MIRROR) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val renderEffect = RenderEffect.createBlurEffect(radius, radius, tileMode)
        this.setRenderEffect(renderEffect)
    }
}

fun View.clearBlurEffect() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.setRenderEffect(null)
    }
}
