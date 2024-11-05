package com.davai.uikit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.pow

/**
 * Кастомная view ConditionedImageView применима только для action элементов на свайп экране
 */
class ConditionedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleAttr) {
    private val minViewSize = resources.getDimensionPixelSize(R.dimen.default_icon_size_52)
    private var imageRect = RectF(0f, 0f, 0f, 0f)
    private var imageBitmap: Bitmap? = null
    private var baseConditionBitmap: Bitmap? = null
    private var secondConditionBitmap: Bitmap? = null
    private val screenWidth = resources.displayMetrics.widthPixels

    /**
     * distanceToBorder используется для вычисления пропорции dx / distanceToBorder, которая будет
     * определять параметр alpha для двух состояний картинки одновременно
     */
    private val distanceToBorder: Float = screenWidth * DISTANCE_TO_BORDER_PERCENTAGE_30
    private val maxAlpha = Color.alpha(Color.WHITE)

    init {
        applyAttributes(
            context,
            attrs,
            defStyleAttr,
            defStyleRes
        )
    }

    private fun applyAttributes(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ConditionedImageView,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                baseConditionBitmap =
                    getDrawable(R.styleable.ConditionedImageView_baseConditionImageId)?.toBitmap()
                secondConditionBitmap =
                    getDrawable(R.styleable.ConditionedImageView_secondConditionImageId)?.toBitmap()
                imageBitmap = baseConditionBitmap
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size = min(
            MeasureSpec.getSize(widthMeasureSpec).coerceAtMost(minViewSize),
            MeasureSpec.getSize(heightMeasureSpec).coerceAtMost(minViewSize)
        )
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        imageRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        imageBitmap?.let {
            canvas.drawBitmap(it, null, imageRect, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                if (isClickable) {
                    tempConditionChange()
                    invalidate()
                    performClick()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun tempConditionChange(duration: Long = CHANGE_CONDITION_DELAY_500_MS) {
        setSecondCondition()
        CoroutineScope(Dispatchers.Main).launch {
            delay(duration)
            setBaseCondition()
            invalidate()
        }
    }

    fun setBaseCondition() {
        imageBitmap = baseConditionBitmap
    }

    fun setSecondCondition() {
        imageBitmap = secondConditionBitmap
    }

    /**
     * Метод updateDynamicAlpha накладывает два состояния (картинки) друг на друга
     * и в зависимости от значения пропорции dx / distanceToBorder устанавливает параметр alpha
     * сразу для двух состояний (картинок).
     * Прозрачность baseConditionDrawable меняется в степенной зависимости от dx, чтобы обеспечить
     * более привлекательную смену прозрачностей картинок.
     * Прозрачность secondConditionDrawable меняется линейно относительно dx
     */
    private fun updateDynamicAlpha(dx: Float) {
        val alpha = if (dx.absoluteValue <= distanceToBorder) {
            1f - dx.absoluteValue / distanceToBorder
        } else {
            1f
        }
        val baseConditionAlpha = if (alpha < 0) 0f else if (alpha > 1) 1f else alpha
        val secondConditionAlpha = if (alpha > 1) 1f else if (alpha < 0) 0f else 1f - alpha

        val baseConditionDrawable = BitmapDrawable(resources, baseConditionBitmap).apply {
            setBounds(0, 0, measuredWidth, measuredHeight)
            this.alpha = (baseConditionAlpha.pow(POWER_OF_5) * maxAlpha).toInt()
        }
        val secondConditionDrawable = BitmapDrawable(resources, secondConditionBitmap).apply {
            setBounds(0, 0, measuredWidth, measuredHeight)
            this.alpha = (secondConditionAlpha * maxAlpha).toInt()
        }

        val layerDrawable = LayerDrawable(arrayOf(baseConditionDrawable, secondConditionDrawable))
        imageBitmap = layerDrawable.toBitmap(measuredWidth, measuredHeight)
        invalidate()

        if (dx >= screenWidth) {
            tempConditionChange()
        }
    }

    /**
     * Для положительных и отрицательных значений dx используем разные методы
     */
    fun updateDynamicAlphaPositive(dx: Float) {
        if (dx < 0) return
        updateDynamicAlpha(dx)
    }

    fun updateDynamicAlphaNegative(dx: Float) {
        if (dx > 0) return
        updateDynamicAlpha(dx)
    }

    private companion object {
        const val CHANGE_CONDITION_DELAY_500_MS = 500L
        const val DISTANCE_TO_BORDER_PERCENTAGE_30 = 0.3f
        const val POWER_OF_5 = 5
    }
}