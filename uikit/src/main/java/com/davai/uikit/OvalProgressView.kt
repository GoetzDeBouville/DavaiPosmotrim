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

class OvalProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progressColor: Int =
        ContextCompat.getColor(context, android.R.color.holo_red_dark)
    private var backgroundColor: Int = ContextCompat.getColor(context, android.R.color.transparent)
    private val refreshDelay: Long = getDisplayRefreshDelay()
    private var scaleX: Float = 1f
    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
    }

    private val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = radius
    }
    private var diameter: Float = 0f
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var sweepAngle: Float = 0f

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
                diameter =
                    getDimension(
                        R.styleable.CustomCircularProgressBar_circleDiameter,
                        DEFAULT_DIAMETER_200
                    )
                radius = diameter / 2
            } finally {
                recycle()
            }
        }

        backgroundPaint.color = backgroundColor
        progressPaint.color = progressColor
        backgroundPaint.strokeWidth = radius
        progressPaint.strokeWidth = radius
    }

    private fun getDisplayRefreshDelay(): Long {
        val displayManger = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val display = displayManger.getDisplay(Display.DEFAULT_DISPLAY)
        return (MS_IN_SECOND / display.refreshRate).toLong()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        scaleX = w.toFloat() / h.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.scale(
            scaleX,
            1f,
            centerX,
            centerY
        )
        canvas.drawCircle(
            centerX,
            centerY,
            radius,
            backgroundPaint
        )

        canvas.drawArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            START_ANGLE_90,
            sweepAngle,
            false,
            progressPaint
        )
        canvas.restore()
    }

    fun setProgressWithAnimation(
        duration: Long = DEFAULT_DURATION_5000_MS,
        fromProgress: Float = 0f,
        toProgress: Float = MAX_PROGRESS,
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

                sweepAngle = (currentProgress / MAX_PROGRESS) * MAX_DEGREES
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
        const val DEFAULT_DIAMETER_200 = 200f
        const val DEFAULT_DURATION_5000_MS = 5000L
    }
}
