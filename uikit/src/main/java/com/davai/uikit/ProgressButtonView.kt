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
    private var progressStrokeWidth = DEFAULT_STROKE_WIDTH_10

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
                    strokeWidth = progressStrokeWidth * 2
                }
                arcPaint.apply {
                    color = progressStrokeColor
                    strokeWidth = progressStrokeWidth * Math.PI.toFloat() / 2
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
        val halfLength = width.toFloat() / 2
        val strokeCornerRadius = cornerRadius.toFloat() / 2
        val totalPathLength = calculateTotalPathLength(strokeCornerRadius)
        val currentLength = calculateCurrentLength(totalPathLength)

        drawFirstHalfTopProgressLine(canvas, halfLength, strokeCornerRadius, currentLength)
        if (currentLength > halfLength - strokeCornerRadius) {
            val remainingLengthAfterFirstCorner =
                drawTopRightCornerArc(canvas, halfLength, strokeCornerRadius, currentLength)
            if (remainingLengthAfterFirstCorner > 0) {
                val remainingLengthAfterRightLine = drawRightVerticalLine(
                    canvas,
                    strokeCornerRadius,
                    remainingLengthAfterFirstCorner
                )
                if (remainingLengthAfterRightLine > 0) {
                    val remainingLengthAfterBottomRightCorner = drawBottomRightCornerArc(
                        canvas,
                        strokeCornerRadius,
                        remainingLengthAfterRightLine
                    )
                    if (remainingLengthAfterBottomRightCorner > 0) {
                        drawBottomLine(
                            canvas,
                            strokeCornerRadius,
                            remainingLengthAfterBottomRightCorner
                        )
                    }
                }
            }
        }
    }

    private fun calculateTotalPathLength(strokeCornerRadius: Float): Float {
        return 2 * (width + height - strokeCornerRadius + Math.PI.toFloat() * strokeCornerRadius)
    }

    private fun calculateCurrentLength(totalPathLength: Float): Float {
        return totalPathLength * progress / NUM_OF_STEPS
    }

    private fun drawFirstHalfTopProgressLine(
        canvas: Canvas,
        halfLength: Float,
        strokeCornerRadius: Float,
        currentLength: Float
    ) {
        canvas.drawLine(
            halfLength,
            0f,
            min(halfLength + currentLength, (width - strokeCornerRadius)),
            0f,
            paint
        )
    }

    private fun drawTopRightCornerArc(
        canvas: Canvas,
        halfLength: Float,
        strokeCornerRadius: Float,
        currentLength: Float
    ): Float {
        val cornerLength = Math.PI.toFloat() / 2f * strokeCornerRadius
        val step = calculateTotalPathLength(strokeCornerRadius) / NUM_OF_STEPS
        val numOfStepsInCornerLength = cornerLength / step
        val degreeByStep = SWEAP_ANGLE_90_DEG / numOfStepsInCornerLength
        val newCurr = currentLength - (halfLength - strokeCornerRadius)

        canvas.drawArc(
            width - 2f * strokeCornerRadius - 2f,
            2f,
            width.toFloat() - 2f,
            2f * strokeCornerRadius + 2f,
            START_ANGLE_270_DEG,
            min(newCurr * degreeByStep, SWEAP_ANGLE_90_DEG),
            false,
            arcPaint
        )
        return newCurr - cornerLength
    }

    private fun drawRightVerticalLine(
        canvas: Canvas,
        strokeCornerRadius: Float,
        remainingLength: Float
    ): Float {
        val lineLength = height - 2 * strokeCornerRadius
        val drawLength = min(remainingLength, lineLength)
        canvas.drawLine(
            width.toFloat(),
            strokeCornerRadius,
            width.toFloat(),
            strokeCornerRadius + drawLength,
            paint
        )
        return remainingLength - drawLength
    }

    private fun drawBottomRightCornerArc(
        canvas: Canvas,
        strokeCornerRadius: Float,
        remainingLength: Float
    ): Float {
        val cornerLength = Math.PI.toFloat() / 2f * strokeCornerRadius
        val step = calculateTotalPathLength(strokeCornerRadius) / NUM_OF_STEPS
        val numOfStepsInCornerLength = cornerLength / step
        val degreeByStep = SWEAP_ANGLE_90_DEG / numOfStepsInCornerLength
        val sweepAngle = min(remainingLength * degreeByStep, SWEAP_ANGLE_90_DEG)

        canvas.drawArc(
            width - 2f * strokeCornerRadius - 2f,
            height - 2f * strokeCornerRadius - 2f,
            width.toFloat() - 2f,
            height.toFloat() - 2f,
            START_ANGLE_0_DEG,
            sweepAngle,
            false,
            arcPaint
        )

        return remainingLength - cornerLength
    }

    private fun drawBottomLine(
        canvas: Canvas,
        strokeCornerRadius: Float,
        remainingLength: Float
    ): Float {
        val bottomLineStartX = width.toFloat() - strokeCornerRadius
        val bottomLineEndX = strokeCornerRadius
        val bottomLineLength = bottomLineStartX - bottomLineEndX
        val drawLength = min(remainingLength, bottomLineLength)

        canvas.drawLine(
            bottomLineStartX,
            height.toFloat(),
            bottomLineStartX - drawLength,
            height.toFloat(),
            paint
        )

        return remainingLength - drawLength
    }


    companion object {
        private const val NEXT_STEP_DELAY = 16L
        private const val NUM_OF_STEPS = 500
        private const val SWEAP_ANGLE_90_DEG = 90f
        private const val START_ANGLE_270_DEG = 270f
        private const val START_ANGLE_0_DEG = 0f
        private const val DEFAULT_STROKE_WIDTH_10 = 10f
    }
}
