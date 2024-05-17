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
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.content.ContextCompat

class BannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val container: LinearLayout by lazy { findViewById(R.id.container) }
    private val text: TextView by lazy { findViewById(R.id.text) }
    private val icon: ImageView by lazy { findViewById(R.id.icon) }

    init {
        LayoutInflater.from(context).inflate(R.layout.banner_view, this, true)

        attrs?.applyStyleable(context, R.styleable.BannerView) {

            val color = getColor(R.styleable.BannerView_banner_background, Color.BLACK)
            setBannerBackgroundColor(color)

            setBannerText(getString(R.styleable.BannerView_banner_text) ?: "")

            val drawable = getResourceId(R.styleable.BannerView_banner_icon, 0)
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

    fun setState(state: Int) {
        when (state) {
            INFO -> {
                setBannerText(ContextCompat.getString(context, R.string.banner_success))
                setBannerBackgroundColor(ContextCompat.getColor(context, R.color.info))
                setBannerIcon(R.drawable.ic_success)
            }
            SUCCESS -> {
                setBannerText(ContextCompat.getString(context, R.string.banner_success))
                setBannerBackgroundColor(ContextCompat.getColor(context, R.color.secondary_base))
                setBannerIcon(R.drawable.ic_success)
            }
            ATTENTION -> {
                setBannerText(ContextCompat.getString(context, R.string.banner_attention))
                setBannerBackgroundColor(ContextCompat.getColor(context, R.color.attention))
                setBannerIcon(R.drawable.ic_attention)
            }
        }
    }

    private fun setBannerText(value: String) {
        text.text = value
    }

    private fun setBannerIcon(value: Int) {
        icon.setImageResource(value)
    }

    private fun setBannerBackgroundColor(@ColorInt color: Int) {
        container.backgroundTintList = ColorStateList.valueOf(color)

        if (color == ContextCompat.getColor(context, R.color.secondary_base)
            || color == ContextCompat.getColor(context, R.color.attention)) {
            text.setTextColor(ContextCompat.getColor(context, R.color.text_base))
            icon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.icon_primary))
        } else {
            text.setTextColor(ContextCompat.getColor(context, R.color.text_light))
            icon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.icon_light))
        }
    }

    companion object {
        const val INFO = 0
        const val SUCCESS = 1
        const val ATTENTION = 2
    }
}