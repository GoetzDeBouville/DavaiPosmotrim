package com.davai.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes

@Suppress("Detekt:MagicNumber")
class TagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var vtText: TextView? = null
    private var tagType: Int = 1

    init {
        initViews()
        applyAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.tag_view, this)
        vtText = findViewById(R.id.tv_tag_view)
    }

    @SuppressLint("ResourceType")
    private fun applyAttributes(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TagView,
            defStyleAttr,
            defStyleRes
        ).apply {
            vtText?.text = getString(R.styleable.TagView_tag_text)
            tagType = getInt(R.styleable.TagView_tag_type, 1)
            setStyle(tagType)
        }
    }

    fun setText(text: String) {
        vtText?.text = text
    }

    fun setChosen() {
        vtText?.setBackgroundResource(R.drawable.tag_secodary_green_background)
        vtText?.setTextColor(context.getColor(R.color.tertiary_base))
    }

    fun setDisabled() {
        vtText?.setBackgroundResource(R.drawable.tag_secondary_gray_background)
        vtText?.setTextColor(context.getColor(R.color.text_caption_dark))
    }

    private fun setStyle(type: Int) {
        when (type) {
            1 -> {
                vtText?.setBackgroundResource(R.drawable.tag_primary_violet_background)
                vtText?.setTextColor(context.getColor(R.color.text_light))
                setPaddings(PADDING_SMALL_HORIZONTAL, PADDING_SMALL_VERTICAL)
            }
            2 -> {
                vtText?.setBackgroundResource(R.drawable.tag_primary_gray_background)
                vtText?.setTextColor(context.getColor(R.color.text_base))
                setPaddings(PADDING_SMALL_HORIZONTAL, PADDING_SMALL_VERTICAL)
            }
            3 -> {
                vtText?.setBackgroundResource(R.drawable.tag_secodary_green_background)
                vtText?.setTextColor(context.getColor(R.color.tertiary_base))
                setPaddings(PADDING_BIG_HORIZONTAL, PADDING_BIG_VERTICAL)
            }
            4 -> {
                vtText?.setBackgroundResource(R.drawable.tag_secondary_gray_background)
                vtText?.setTextColor(context.getColor(R.color.text_caption_dark))
                setPaddings(PADDING_BIG_HORIZONTAL, PADDING_BIG_VERTICAL)
            }
        }
    }

    private fun setPaddings(horizontal: Int, vertical: Int) {
        vtText?.setPadding(
            (horizontal * resources.displayMetrics.density).toInt(),
            (vertical * resources.displayMetrics.density).toInt(),
            (horizontal * resources.displayMetrics.density).toInt(),
            (vertical * resources.displayMetrics.density).toInt(),
        )
    }

    private companion object {
        const val PADDING_SMALL_HORIZONTAL = 12
        const val PADDING_SMALL_VERTICAL = 4
        const val PADDING_BIG_HORIZONTAL = 20
        const val PADDING_BIG_VERTICAL = 8
    }
}