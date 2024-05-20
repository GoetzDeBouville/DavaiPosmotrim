package com.davai.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes

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
                setPaddings(12, 4)
            }
            2 -> {
                vtText?.setBackgroundResource(R.drawable.tag_primary_gray_background)
                vtText?.setTextColor(context.getColor(R.color.text_base))
                setPaddings(12, 4)
             }
            3 -> {
                vtText?.setBackgroundResource(R.drawable.tag_secodary_green_background)
                vtText?.setTextColor(context.getColor(R.color.tertiary_base))
                setPaddings(20, 8)
            }
            4 -> {
                vtText?.setBackgroundResource(R.drawable.tag_secondary_gray_background)
                vtText?.setTextColor(context.getColor(R.color.text_caption_dark))
                setPaddings(20, 8)
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
}