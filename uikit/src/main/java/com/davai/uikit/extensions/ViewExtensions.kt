package com.davai.uikit.extensions

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View

/**
 * Методы управления blur effect
 */
fun View.applyBlurEffect(radiusX: Float = 40f, radiusY: Float = 40f) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val renderEffect = RenderEffect.createBlurEffect(radiusX, radiusY, Shader.TileMode.MIRROR)
        this.setRenderEffect(renderEffect)
    }
}

fun View.applyBlurEffect(radius: Float = 40f) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val renderEffect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.MIRROR)
        this.setRenderEffect(renderEffect)
    }
}

fun View.clearBlurEffect() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.setRenderEffect(null)
    }
}
