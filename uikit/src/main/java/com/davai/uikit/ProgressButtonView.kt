package com.davai.uikit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

class ProgressButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {
    private var progress = 0
    private var progressStrokeColor = Color.GREEN
    private var progressStrokeWidth = 10f

    private val paint = Paint()
    private val arcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    init {
        applyAttributeSet(context, attrs)
    }

    private fun applyAttributeSet(
        context: Context,
        attrs: AttributeSet?
    ) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButtonView,
            0,
            0
        ).apply {
            try {
                progressStrokeColor =
                    getColor(
                        R.styleable.ProgressButtonView_progress_stroke_color,
                        Color.GREEN
                    )
                progressStrokeWidth =
                    getDimension(R.styleable.ProgressButtonView_progress_stroke_width, 0f)
                paint.apply {
                    color = progressStrokeColor
                    strokeWidth = progressStrokeWidth
                }
                arcPaint.apply {
                    color = progressStrokeColor
                    strokeWidth = progressStrokeWidth / 2
                }
            } finally {
                recycle()
            }
        }
    }

    fun startAnimation(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            while (progress < NUM_OF_STEPS) {
                delay(NEXT_STEP_DELAY)
                ++progress
                invalidate()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val totalPathLength =
            2 * width +
                    2 * height -
                    8 * cornerRadius +
                    2 * Math.PI.toFloat() * cornerRadius
        val curr = totalPathLength * progress / NUM_OF_STEPS
        canvas.drawLine(
            0.5f * width,
            0f,
            min(0.5f * width + curr, width - cornerRadius - 0f),
            0f,
            paint
        )
        if (curr > 0.5f * width - cornerRadius) {
            val cornerLength = Math.PI.toFloat() / 2f * cornerRadius
            val step = totalPathLength / NUM_OF_STEPS
            val numOfStepsInCornerLength = cornerLength / step
            val degreeByStep = SWEAP_ANGLE_90_DEG / numOfStepsInCornerLength
            val newCurr = curr - (0.5f * width - cornerRadius)
            canvas.drawArc(
                width - 2f * cornerRadius - 2f,
                2f,
                width.toFloat() - 2f,
                2f * cornerRadius + 2f,
                START_ANGLE_270_DEG,
                min(newCurr * degreeByStep, SWEAP_ANGLE_90_DEG),
                false,
                arcPaint
            )
        }
    }

    companion object {
        private const val NEXT_STEP_DELAY = 50L
        private const val NUM_OF_STEPS = 500
        private const val SWEAP_ANGLE_90_DEG = 90f
        private const val START_ANGLE_270_DEG = 270f
    }
}
