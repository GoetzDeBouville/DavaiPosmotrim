package com.davai.uikit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
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
import kotlin.math.min

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
            MotionEvent.ACTION_DOWN -> {
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (isClickable) {
                    tempConditionChange()
                    invalidate()
                    performClick()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun tempConditionChange() {
        setSecondCondition()
        CoroutineScope(Dispatchers.Main).launch {
            delay(CHANGE_CONDITION_DELAY_500_MS)
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

    private companion object {
        const val CHANGE_CONDITION_DELAY_500_MS = 500L
    }
}