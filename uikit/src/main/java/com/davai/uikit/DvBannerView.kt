package com.davai.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes

class DvBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val container: LinearLayout by lazy { findViewById(R.id.container) }
    private val text: TextView by lazy { findViewById(R.id.text) }
    private val icon: ImageView by lazy { findViewById(R.id.icon) }

    init {
        inflate(context, R.layout.dv_banner_view, this)

        attrs?.applyStyleable(context, R.styleable.DvBannerView) {
            val color = getColor(R.styleable.DvBannerView_bannerBackground, Color.BLACK)
            container.backgroundTintList = ColorStateList.valueOf(color)

            text.text = getString(R.styleable.DvBannerView_bannerText)

            val drawable = getDrawable(R.styleable.DvBannerView_bannerIcon)
            icon.setImageDrawable(drawable)
        }
    }

    private fun AttributeSet.applyStyleable(
        context: Context,
        @StyleableRes id: IntArray,
        action: TypedArray.() -> Unit
    ) {
        val typedArray = context.obtainStyledAttributes(this, id)
        typedArray.action()
        typedArray.recycle()
    }
}