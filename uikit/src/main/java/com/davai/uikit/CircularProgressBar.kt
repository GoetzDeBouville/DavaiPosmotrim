package com.davai.uikit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.hardware.display.DisplayManager
import android.util.AttributeSet
import android.view.Display
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progressColor: Int =
        ContextCompat.getColor(context, android.R.color.holo_blue_light)
    private var backgroundColor: Int = ContextCompat.getColor(context, android.R.color.darker_gray)
    private var progress: Float = 0f
    private var circleDiameter: Float = 0f
    private val refreshDelay: Long = getDisplayRefreshDelay()
    private var arrowRadius: Float = 0f

    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    init {
        applyAttributes(context, attrs)
    }

    private fun applyAttributes(
        context: Context,
        attrs: AttributeSet?
    ) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCircularProgressBar,
            0,
            0
        ).apply {
            try {
                progressColor =
                    getColor(R.styleable.CustomCircularProgressBar_progressColor, progressColor)
                backgroundColor =
                    getColor(R.styleable.CustomCircularProgressBar_backgroundColor, backgroundColor)
                circleDiameter =
                    getDimension(R.styleable.CustomCircularProgressBar_circleDiameter, 0f)
                arrowRadius = circleDiameter / 2
            } finally {
                recycle()
            }
        }

        backgroundPaint.color = backgroundColor
        progressPaint.color = progressColor
    }

    private fun getDisplayRefreshDelay(): Long {
        val displayManger = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val display = displayManger.getDisplay(Display.DEFAULT_DISPLAY)
        return (MS_IN_SECOND / display.refreshRate).toLong()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val diameter = if (circleDiameter > 0) circleDiameter else min(width, height)
        val radius = diameter / 2
        val centerX = width / 2
        val centerY = height / 2

        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        val sweepAngle = (progress / MAX_PROGRESS) * MAX_DEGREES
        val left = centerX - radius
        val top = centerY - radius
        val right = centerX + radius
        val bottom = centerY + radius
        canvas.drawArc(
            left,
            top,
            right,
            bottom,
            START_ANGLE_90,
            sweepAngle,
            false,
            progressPaint
        )
    }

    fun setProgressWithAnimation(
        duration: Long,
        fromProgress: Float = 0f,
        toProgress: Float,
        doOnFinish: (() -> Unit)? = null
    ) {
        val endProgress = toProgress.coerceIn(0f, MAX_PROGRESS)

        CoroutineScope(Dispatchers.Main).launch {
            val startTime = System.currentTimeMillis()
            var fraction = 0f

            while (fraction < 1f) {
                val elapsedTime = System.currentTimeMillis() - startTime
                fraction = (elapsedTime.toFloat() / duration).coerceIn(0f, 1f)
                val currentProgress = fromProgress + (endProgress - fromProgress) * fraction

                progress = currentProgress
                invalidate()
                delay(refreshDelay)
            }
            doOnFinish?.invoke()
        }
    }

    private companion object {
        const val MAX_DEGREES = 360f
        const val MAX_PROGRESS = 100f
        const val MS_IN_SECOND = 1000
        const val START_ANGLE_90 = -90f
    }
}
