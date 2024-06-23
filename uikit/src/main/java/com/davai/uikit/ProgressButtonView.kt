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

/**
 * Прогресс реализован как пошаговая перерисовка по периметору с линейной зависимостью от времени.
 */
@Suppress("Detekt.LargeClass")
class ProgressButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {
    private var progress = 0
    private var numOfSteps: Int = 0
    private var progressStrokeColor = Color.GREEN
    private var progressStrokeWidth = DEFAULT_STROKE_WIDTH_10
    private val refreshDelay: Long = getDisplayRefreshDelay()
    private var currentLength = 0f
    private val paint = Paint()
    private val arcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    // Выносим из onDraw инициализацию объектов
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

    private fun applyAttributeSet(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButtonView,
            0,
            0
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
                cornerRadius *= 2 // корректировка радиуса для прорисовки
            } finally {
                recycle()
            }
        }
    }

    /**
     * Метод startAnimation использует системное время обновления экрана (переменная refreshDelay)
     * и вычисляет число обновлений с учетом частоты обновления экрана (т.к. экраны могут
     * обновляться с частотой и 60 Гц и 144 Гц или даже комбинировать частоту обновления).
     */
    fun animateProgress(
        coroutineScope: CoroutineScope,
        duration: Long = DEFAULT_DURATION_5000_MS,
        doOnFinish: (() -> Unit)? = null
    ) {
        numOfSteps = (duration / refreshDelay).toInt()
        coroutineScope.launch {
            while (progress < numOfSteps) {
                delay(refreshDelay)
                ++progress
                invalidate()
            }
            doOnFinish?.invoke()
        }
    }

    /**
     * Прорисовка прогресса выполняется по принципу цепочки и чейнит каждый последующий этап
     * прорисовки к предыдущему через проверку остатка длины
     * Первая половина верхнего открезка периметра кнопки прорисовывается без привязок.
     * Верхний правый угол привязан к currentLength, чтобы не создавать переменную для первой
     * половины верхнего отрезка периметра кнопки
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        updateCurrentLength()
        canvas.drawFirstHalfTopHorizontalLine(currentLength)
        if (currentLength > width / 2f - cornerRadius / 2f) {
            remainingLengthAfterTopRightCorner = canvas.drawCornerArc(
                currentLength - (width / 2f - cornerRadius / 2f), CornerType.TOP_RIGHT
            )
        }
        if (remainingLengthAfterTopRightCorner > 0) {
            remainingLengthAfterRightVerticalLine =
                canvas.drawRightVerticalLine(remainingLengthAfterTopRightCorner)
        }
        if (remainingLengthAfterRightVerticalLine > 0) {
            remainingLengthAfterBottomRightCorner =
                canvas.drawCornerArc(remainingLengthAfterRightVerticalLine, CornerType.BOTTOM_RIGHT)
        }
        if (remainingLengthAfterBottomRightCorner > 0) {
            remainingLengthAfterBottomLine =
                canvas.drawBottomLine(remainingLengthAfterBottomRightCorner)
        }
        if (remainingLengthAfterBottomLine > 0) {
            remainingLengthAfterBottomLeftCorner =
                canvas.drawCornerArc(remainingLengthAfterBottomLine, CornerType.BOTTOM_LEFT)
        }
        if (remainingLengthAfterBottomLeftCorner > 0) {
            remainingLengthAfterLeftVerticalLine =
                canvas.drawLeftVerticalLine(remainingLengthAfterBottomLeftCorner)
        }
        if (remainingLengthAfterLeftVerticalLine > 0) {
            remainingLengthAfterTopLeftCorner =
                canvas.drawCornerArc(remainingLengthAfterLeftVerticalLine, CornerType.TOP_LEFT)
        }
        if (remainingLengthAfterTopLeftCorner > 0f) {
            canvas.drawSecondHalfTopProgressLine(remainingLengthAfterTopLeftCorner)
        }
    }

    /**
     * Универсальный метод для прорисовки углов. Магические это эмпирические значения и будут
     * применимы для конкретноой толщины линии прогресса. Метод собирает координаты квадрат и
     * передает их в drawArc
     * значение COEFFICIENT_CONSTANT_2 влияет на смещение линии прогресса на углах и установлено 2
     * после экспериментов
     */
    private fun Canvas.drawCornerArc(remainingLength: Float, cornerType: CornerType): Float {
        val cornerLength = Math.PI.toFloat() * cornerRadius / 2 - progressStrokeWidth
        val sweepAngle = min(remainingLength / cornerLength * ANGLE_90_DEG, ANGLE_90_DEG)
        val left =
            if (cornerType == CornerType.TOP_RIGHT || cornerType == CornerType.BOTTOM_RIGHT) {
                width - cornerRadius - progressStrokeWidth
            } else {
                COEFFICIENT_CONSTANT_2
            }
        val top =
            if (cornerType == CornerType.BOTTOM_RIGHT || cornerType == CornerType.BOTTOM_LEFT) {
                height - cornerRadius - progressStrokeWidth
            } else {
                COEFFICIENT_CONSTANT_2
            }
        val right =
            if (cornerType == CornerType.TOP_RIGHT || cornerType == CornerType.BOTTOM_RIGHT) {
                width - COEFFICIENT_CONSTANT_2
            } else {
                cornerRadius + COEFFICIENT_CONSTANT_2
            }
        val bottom =
            if (cornerType == CornerType.BOTTOM_RIGHT || cornerType == CornerType.BOTTOM_LEFT) {
                height - COEFFICIENT_CONSTANT_2
            } else {
                cornerRadius + COEFFICIENT_CONSTANT_2
            }
        this.drawArc(
            left,
            top,
            right,
            bottom,
            cornerType.startAngle,
            sweepAngle,
            false,
            arcPaint
        )
        return remainingLength - cornerLength
    }

    private fun Canvas.drawFirstHalfTopHorizontalLine(currentLength: Float): Float {
        val lineLength = width - cornerRadius / 2f
        val drawLength = min(currentLength, lineLength - width / 2)

        this.drawLine(
            width / 2f,
            0f,
            min(width / 2f + currentLength, lineLength),
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

    private fun calculateTotalPathLength(): Float {
        return 2 * (width + height - cornerRadius / 2 + Math.PI.toFloat() * cornerRadius / 2)
    }

    private fun updateCurrentLength() {
        currentLength = calculateTotalPathLength() * progress / numOfSteps
    }

    /**
     * Метод getDisplayRefreshDelay получает значения времени обновления экрана.
     */
    private fun getDisplayRefreshDelay(): Long {
        val displayManger = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val display = displayManger.getDisplay(Display.DEFAULT_DISPLAY)
        return (MS_IN_SECOND / display.refreshRate).toLong()
    }

    companion object {
        private const val ANGLE_0_DEG = 0f
        private const val ANGLE_90_DEG = 90f
        private const val ANGLE_180_DEG = 180f
        private const val ANGLE_270_DEG = 270f
        private const val DEFAULT_STROKE_WIDTH_10 = 10f
        private const val COEFFICIENT_CONSTANT_2 = 2f
        private const val MS_IN_SECOND = 1000L
        private const val DEFAULT_DURATION_5000_MS = 5000L

        enum class CornerType(val startAngle: Float) {
            TOP_RIGHT(ANGLE_270_DEG),
            BOTTOM_RIGHT(ANGLE_0_DEG),
            BOTTOM_LEFT(ANGLE_90_DEG),
            TOP_LEFT(ANGLE_180_DEG)
        }
    }
}
