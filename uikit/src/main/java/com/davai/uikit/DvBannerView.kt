package com.davai.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
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
        LayoutInflater.from(context).inflate(R.layout.dv_banner_view, this, true)

        attrs?.applyStyleable(context, R.styleable.DvBannerView) {
            val color = getColor(R.styleable.DvBannerView_bannerBackground, Color.BLACK)
            setBannerBackgroundColor(color)

            setBannerText(getString(R.styleable.DvBannerView_bannerText) ?: "")

            val drawable = getResourceId(R.styleable.DvBannerView_bannerIcon, 0)
            setBannerIcon(drawable)
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

    fun setBannerText(value: String) {
        text.text = value
    }

    fun setBannerIcon(value: Int) {
        icon.setImageResource(value)
    }

    fun setBannerBackgroundColor(value: Int) {
        container.backgroundTintList = ColorStateList.valueOf(value)

        if (value == context.getColor(R.color.secondary_base) || value == context.getColor(R.color.attention)) {
            text.setTextColor(context.getColor(R.color.text_base))
            icon.imageTintList = ColorStateList.valueOf(context.getColor(R.color.icon_primary))
        } else {
            text.setTextColor(context.getColor(R.color.text_light))
            icon.imageTintList = ColorStateList.valueOf(context.getColor(R.color.icon_light))
        }
    }
}