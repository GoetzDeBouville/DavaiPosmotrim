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

    //    private val strokeCornerRadius = cornerRadius / 2f
    private val paint = Paint()
    private val arcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    //? Выносим из onDraw создание объектов
    private var remainingLengthAfterTopRightCorner = 0f
    private var remainingLengthAfterRightVerticalLine = 0f
    private var remainingLengthAfterBottomRightCorner = 0f
    private var remainingLengthAfterBottomLine = 0f
    private var remainingLengthAfterBottomLeftCorner = 0f
    private var remainingLengthAfterLeftVerticalLine = 0f
    private var remainingLengthAfterTopLeftCorner = 0f

    init {
        applyAttributeSet(context, attrs)
    }

    private fun applyAttributeSet(
        context: Context, attrs: AttributeSet?
    ) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.ProgressButtonView, 0, 0
        ).apply {
            try {
                progressStrokeColor = getColor(
                    R.styleable.ProgressButtonView_progress_stroke_color, Color.GREEN
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

    /**
     * Метод getDisplayRefreshDelay получает значения времени обновления экрана.
     */
    private fun getDisplayRefreshDelay(): Long {
        val displayManger = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val display = displayManger.getDisplay(Display.DEFAULT_DISPLAY)
        return (MS_IN_SECOND / display.refreshRate).toLong()
    }

    /**
     * Метод startAnimation использует системное время обновления экрана (переменная refreshDelay)
     * и вычисляет число обновлений с учетом частоты обновления экрана (т.к. экраны могут
     * обновляться с частотой и 60 Гц и 144 Гц или даже комбинировать частоту обновления).
     */
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

    /**
     * Прорисовка прогресса выполняется по принципу цепочки и чейнит каждый последующий этап
     * прорисовки к предыдущему через проверку остатка длины
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val totalPathLength = calculateTotalPathLength(cornerRadius / 2f)
        val currentLength = calculateCurrentLength(totalPathLength)
        // Прорисовываем первую половину  верхнего отрезка периметра кнопки
        canvas.drawFirstHalfTopHorizontalLine(width / 2f, currentLength)
        // привязываемся к величине текущей длины чтобы не создавать лишнюю переменную
        if (currentLength > width / 2f - cornerRadius / 2f) remainingLengthAfterTopRightCorner =
            canvas.drawCornerArc(
                currentLength - (width / 2f - cornerRadius / 2f), ANGLE_270_DEG
            )

        if (remainingLengthAfterTopRightCorner > 0) remainingLengthAfterRightVerticalLine =
            canvas.drawRightVerticalLine(remainingLengthAfterTopRightCorner)

        if (remainingLengthAfterRightVerticalLine > 0) remainingLengthAfterBottomRightCorner =
            canvas.drawCornerArc(remainingLengthAfterRightVerticalLine, ANGLE_0_DEG)

        if (remainingLengthAfterBottomRightCorner > 0) remainingLengthAfterBottomLine =
            canvas.drawBottomLine(remainingLengthAfterBottomRightCorner)

        if (remainingLengthAfterBottomLine > 0) remainingLengthAfterBottomLeftCorner =
            canvas.drawCornerArc(remainingLengthAfterBottomLine, ANGLE_90_DEG)

        if (remainingLengthAfterBottomLeftCorner > 0) remainingLengthAfterLeftVerticalLine =
            canvas.drawLeftVerticalLine(remainingLengthAfterBottomLeftCorner)

        if (remainingLengthAfterLeftVerticalLine > 0) remainingLengthAfterTopLeftCorner =
            canvas.drawCornerArc(remainingLengthAfterLeftVerticalLine, ANGLE_180_DEG)

        if (remainingLengthAfterTopLeftCorner > 0f) canvas.drawSecondHalfTopProgressLine(
            remainingLengthAfterTopLeftCorner
        )
    }

    /**
     * Универсальный метод для прорисовки углов. Магические это эмпирические значения и будут
     * применимы для конкретноой толщины линии прогресса.
     */
    private fun Canvas.drawCornerArc(
        remainingLength: Float, startAngle: Float
    ): Float {
        val strokeCornerRadius = cornerRadius / 2f
        val cornerLength = Math.PI.toFloat() / 2f * strokeCornerRadius
        val sweepAngle = min(remainingLength / cornerLength * ANGLE_90_DEG, ANGLE_90_DEG)

        val left = if (startAngle == ANGLE_270_DEG || startAngle == ANGLE_0_DEG) {
            width - 2f * strokeCornerRadius - 2f
        } else 2f
        val top = if (startAngle == ANGLE_0_DEG || startAngle == ANGLE_90_DEG) {
            height - 2f * strokeCornerRadius - 2f
        } else 2f
        val right = if (startAngle == ANGLE_270_DEG || startAngle == ANGLE_0_DEG) {
            width.toFloat() - 2f
        } else 2f * strokeCornerRadius + 2f
        val bottom = if (startAngle == ANGLE_0_DEG || startAngle == ANGLE_90_DEG) {
            height.toFloat() - 2f
        } else 2f * strokeCornerRadius + 2f

        this.drawArc(
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

    private fun Canvas.drawFirstHalfTopHorizontalLine(
        halfLength: Float, currentLength: Float
    ): Float {
        val lineLength = width - halfLength - cornerRadius / 2
        val drawLength = min(currentLength, lineLength)

        this.drawLine(
            halfLength,
            0f,
            min(halfLength + currentLength, (width - cornerRadius / 2f)),
            0f,
            paint
        )

        return currentLength - drawLength
    }

    private fun Canvas.drawRightVerticalLine(
        remainingLength: Float
    ): Float {
        val lineLength = height - cornerRadius.toFloat()
        val drawLength = min(remainingLength, lineLength)
        this.drawLine(
            width.toFloat(),
            cornerRadius / 2f,
            width.toFloat(),
            cornerRadius / 2f + drawLength,
            paint
        )
        return remainingLength - drawLength
    }

    private fun Canvas.drawBottomLine(remainingLength: Float): Float {
        val bottomLineStartX = width - cornerRadius / 2f
        val bottomLineLength = bottomLineStartX - cornerRadius / 2f
        val drawLength = min(remainingLength, bottomLineLength)

        this.drawLine(
            bottomLineStartX,
            height.toFloat(),
            bottomLineStartX - drawLength,
            height.toFloat(),
            paint
        )

        return remainingLength - drawLength
    }

    private fun Canvas.drawLeftVerticalLine(remainingLength: Float): Float {
        val lineLength = height - cornerRadius.toFloat()
        val drawLength = min(remainingLength, lineLength)
        this.drawLine(
            0f,
            height.toFloat() - cornerRadius / 2f,
            0f,
            height.toFloat() - cornerRadius / 2f - drawLength,
            paint
        )
        return remainingLength - drawLength
    }


    private fun Canvas.drawSecondHalfTopProgressLine(remainingLength: Float) {
        val lineLength = width / 2 - cornerRadius / 2f
        val drawLength = min(remainingLength, lineLength)

        this.drawLine(
            cornerRadius / 2f,
            0f,
            cornerRadius / 2f + drawLength,
            0f,
            paint
        )
    }

    companion object {
        private const val ANGLE_0_DEG = 0f
        private const val ANGLE_90_DEG = 90f
        private const val ANGLE_180_DEG = 180f
        private const val ANGLE_270_DEG = 270f
        private const val DEFAULT_STROKE_WIDTH_10 = 10f
        const val MS_IN_SECOND = 1000
        const val DEFAULT_DURATION_5000_MS = 5000L
    }
}
