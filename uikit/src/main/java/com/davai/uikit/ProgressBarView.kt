package com.davai.uikit

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class ProgressBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var rotationAngle = 0f
    private val rectF = RectF()
    private lateinit var gradient: SweepGradient

    init {
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 1400L
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                rotationAngle = animation.animatedValue as Float
                invalidate()
            }
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            start()
        }

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 25f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = Math.min(cx, cy) - paint.strokeWidth / 2

        rectF.set(cx - radius, cy - radius, cx + radius, cy + radius)

        val colors = intArrayOf(Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.WHITE)
        val positions = floatArrayOf(0f, 0.3f, 0.6f, 1f)
        gradient = SweepGradient(cx, cy, colors, positions)

        paint.shader = gradient

        canvas.save()
        canvas.rotate(rotationAngle, cx, cy)
        canvas.drawArc(rectF, 0f, 360f, false, paint)
        canvas.restore()
    }
}