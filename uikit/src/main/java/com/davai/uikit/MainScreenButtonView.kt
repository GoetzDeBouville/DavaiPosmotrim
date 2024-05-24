package com.davai.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

class MainScreenButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val label: TextView by lazy { findViewById(R.id.label) }
    private val icon: ImageView by lazy { findViewById(R.id.icon) }
    private val container: LinearLayout by lazy { findViewById(R.id.container) }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.main_screen_button_view, this, true)
    }

    private fun setBackgroundTint(@ColorInt color: Int) {
        container.backgroundTintList = ColorStateList.valueOf(color)

        if (color == ContextCompat.getColor(context, R.color.secondary_base)) {
            val onColor = ContextCompat.getColor(context, R.color.text_base)
            label.setTextColor(onColor)
            icon.imageTintList = ColorStateList.valueOf(onColor)
        }
    }

    private fun setIcon(@DrawableRes drawable: Int) {
        icon.setImageResource(drawable)
    }

    private fun setLabel(text: String) {
        label.text = text
    }

    fun setState(state: Int) {
        when (state) {
            CREATE -> {
                setIcon(R.drawable.ic_arrow_exclude)
                setLabel(context.getString(R.string.create_session))
                setBackgroundTint(ContextCompat.getColor(context, R.color.primary_base))
            }
            FAVORITE -> {
                setIcon(R.drawable.ic_heart_exclude)
                setLabel(context.getString(R.string.favorite))
                setBackgroundTint(ContextCompat.getColor(context, R.color.secondary_base))
            }
            JOIN -> {
                setIcon(R.drawable.ic_arrow_exclude)
                setLabel(context.getString(R.string.join_session))
                setBackgroundTint(ContextCompat.getColor(context, R.color.info))
            }
        }
    }

    companion object {
        const val CREATE = 0
        const val FAVORITE = 1
        const val JOIN = 2
    }
}