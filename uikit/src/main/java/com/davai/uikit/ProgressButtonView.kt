package com.davai.uikit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.display.DisplayManager
import android.util.AttributeSet
import android.view.Display
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
    private val refreshDelay: Long = getDisplayRefreshDelay()
    private var numOfSteps: Int = 0

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

    private fun getDisplayRefreshDelay(): Long {
        val displayManger = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val display = displayManger.getDisplay(Display.DEFAULT_DISPLAY)
        return (MS_IN_SECOND / display.refreshRate).toLong()
    }

    fun startAnimation(coroutineScope: CoroutineScope, duration: Long = DEFAULT_DURATION_5000_MS) {
        numOfSteps = (duration / refreshDelay).toInt()
        coroutineScope.launch {
            while (progress < numOfSteps) {
                delay(refreshDelay)
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

        drawFirstHalfTopHorizontalLine(
            canvas,
            strokeCornerRadius,
            halfLength,
            currentLength
        )
        val remainingLengthAfterTopRightCorner =
            if (currentLength > halfLength - strokeCornerRadius) {
                drawCornerArc(
                    canvas,
                    strokeCornerRadius,
                    currentLength - (halfLength - strokeCornerRadius),
                    ANGLE_270_DEG
                )
            } else 0f

        val remainingLengthAfterRightVerticalLine =
            if (remainingLengthAfterTopRightCorner > 0) drawRightVerticalLine(
                canvas,
                strokeCornerRadius,
                remainingLengthAfterTopRightCorner
            ) else 0f

        val remainingLengthAfterBottomRightCorner = if (remainingLengthAfterRightVerticalLine > 0) {
            drawCornerArc(
                canvas,
                strokeCornerRadius,
                remainingLengthAfterRightVerticalLine,
                ANGLE_0_DEG
            )
        } else 0f

        val remainingLengthAfterBottomLine = if (remainingLengthAfterBottomRightCorner > 0) {
            drawBottomLine(
                canvas,
                strokeCornerRadius,
                remainingLengthAfterBottomRightCorner
            )
        } else 0f

        val remainingLengthAfterBottomLeftCorner = if (remainingLengthAfterBottomLine > 0) {
            drawCornerArc(
                canvas,
                strokeCornerRadius,
                remainingLengthAfterBottomLine,
                ANGLE_90_DEG
            )
        } else 0f
        val remainingLengthAfterLeftVerticalLine = if (remainingLengthAfterBottomLeftCorner > 0) {
            drawLeftVerticalLine(
                canvas,
                strokeCornerRadius,
                remainingLengthAfterBottomLeftCorner
            )
        } else 0f
        val remainingLengthAfterTopLeftCorner = if (remainingLengthAfterLeftVerticalLine > 0) {
            drawCornerArc(
                canvas,
                strokeCornerRadius,
                remainingLengthAfterLeftVerticalLine,
                ANGLE_180_DEG
            )
        } else 0f

        if (remainingLengthAfterTopLeftCorner > 0f) {
            drawSecondHalfTopProgressLine(
                canvas,
                strokeCornerRadius,
                remainingLengthAfterTopLeftCorner
            )
        }
    }

    private fun drawCornerArc(
        canvas: Canvas,
        strokeCornerRadius: Float,
        remainingLength: Float,
        startAngle: Float
    ): Float {
        val cornerLength = Math.PI.toFloat() / 2f * strokeCornerRadius
        val sweepAngle =
            min(remainingLength / cornerLength * ANGLE_90_DEG, ANGLE_90_DEG)

        val left =
            if (startAngle == ANGLE_270_DEG || startAngle == 0f) {
                width - 2f * strokeCornerRadius - 2f
            } else {
                2f
            }
        val top =
            if (startAngle == 0f || startAngle == ANGLE_90_DEG) {
                height - 2f * strokeCornerRadius - 2f
            } else {
                2f
            }
        val right =
            if (startAngle == ANGLE_270_DEG || startAngle == 0f) {
                width.toFloat() - 2f
            } else {
                2f * strokeCornerRadius + 2f
            }
        val bottom =
            if (startAngle == 0f || startAngle == ANGLE_90_DEG) {
                height.toFloat() - 2f
            } else {
                2f * strokeCornerRadius + 2f
            }

        canvas.drawArc(
            left,
            top,
            right,
            bottom,
            startAngle,
            sweepAngle,
            false,
            arcPaint
        )

        return remainingLength - cornerLength
    }

    private fun calculateTotalPathLength(strokeCornerRadius: Float): Float {
        return 2 * (width + height - strokeCornerRadius + Math.PI.toFloat() * strokeCornerRadius)
    }

    private fun calculateCurrentLength(totalPathLength: Float): Float {
        return totalPathLength * progress / numOfSteps
    }

    private fun drawFirstHalfTopHorizontalLine(
        canvas: Canvas,
        strokeCornerRadius: Float,
        halfLength: Float,
        currentLength: Float
    ): Float {
        val lineLength = width.toFloat() - halfLength - strokeCornerRadius
        val drawLength = min(currentLength, lineLength)

        canvas.drawLine(
            halfLength,
            0f,
            min(halfLength + currentLength, (width - strokeCornerRadius)),
            0f,
            paint
        )

        return currentLength - drawLength
    }

    private fun drawRightVerticalLine(
        canvas: Canvas,
        strokeCornerRadius: Float,
        remainingLength: Float
    ): Float {
        val drawLength = min(remainingLength, height - 2 * strokeCornerRadius)
        canvas.drawLine(
            width.toFloat(),
            strokeCornerRadius,
            width.toFloat(),
            strokeCornerRadius + drawLength,
            paint
        )
        return remainingLength - drawLength
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

    private fun drawLeftVerticalLine(
        canvas: Canvas,
        strokeCornerRadius: Float,
        remainingLength: Float
    ): Float {
        val lineLength = height - 2 * strokeCornerRadius
        val drawLength = min(remainingLength, lineLength)
        canvas.drawLine(
            0f,
            height.toFloat() - strokeCornerRadius,
            0f,
            height.toFloat() - strokeCornerRadius - drawLength,
            paint
        )
        return remainingLength - drawLength
    }


    private fun drawSecondHalfTopProgressLine(
        canvas: Canvas,
        strokeCornerRadius: Float,
        remainingLength: Float
    ) {
        val lineLength = width.toFloat() / 2 - strokeCornerRadius
        val drawLength = min(remainingLength, lineLength)

        canvas.drawLine(
            strokeCornerRadius,
            0f,
            strokeCornerRadius + drawLength,
            0f,
            paint
        )
    }

    companion object {
        private const val DEFAULT_NUM_OF_STEPS = 500
        private const val ANGLE_0_DEG = 0f
        private const val ANGLE_90_DEG = 90f
        private const val ANGLE_180_DEG = 180f
        private const val ANGLE_270_DEG = 270f
        private const val DEFAULT_STROKE_WIDTH_10 = 10f
        const val MS_IN_SECOND = 1000
        const val DEFAULT_DURATION_5000_MS = 5000L
    }
}
