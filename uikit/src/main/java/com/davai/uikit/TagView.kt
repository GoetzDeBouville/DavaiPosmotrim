package com.davai.uikit

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

    private var tvTagText: TextView? = null
    private var tagType: Int = 1

    init {
        initViews()
        applyAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.tag_view, this)
        tvTagText = findViewById(R.id.tv_tag_view)
    }

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
            try {
                tvTagText?.text = getString(R.styleable.TagView_tag_text)
                tagType = getInt(R.styleable.TagView_tag_type, 1)
                setStyle(tagType)
            } finally {
                recycle()
            }
        }
    }

    fun setText(text: String) {
        tvTagText?.text = text
    }


    fun changeStyle(style: Style) {
        when (style) {
            Style.PRIMARY_VIOLET -> setStyle(STYLE_PRIMARY_VIOLET)
            Style.PRIMARY_GRAY -> setStyle(STYLE_PRIMARY_GRAY)
            Style.SECONDARY_GREEN -> setStyle(STYLE_SECONDARY_GREEN)
            Style.SECONDARY_GRAY -> setStyle(STYLE_SECONDARY_GRAY)
            Style.ONBOARDING_YELLOW -> setStyle(STYLE_ONBOARDING_YELLOW)
            Style.ONBOARDING_VIOLET -> setStyle(STYLE_ONBOARDING_VIOLET)
        }
    }

    @Suppress("Detekt.LongMethod")
    fun setStyle(type: Int) {
        when (type) {
            STYLE_PRIMARY_VIOLET -> {
                tvTagText?.let {
                    it.setBackgroundResource(R.drawable.tag_primary_violet_background)
                    it.setTextColor(context.getColor(R.color.text_light))
                }
                setPaddings(PADDING_HORIZONTAL_12_DP, PADDING_VERTICAL_4_DP)
            }

            STYLE_PRIMARY_GRAY -> {
                tvTagText?.let {
                    it.setBackgroundResource(R.drawable.tag_primary_gray_background)
                    it.setTextColor(context.getColor(R.color.text_base))
                }
                setPaddings(PADDING_HORIZONTAL_12_DP, PADDING_VERTICAL_4_DP)
            }

            STYLE_SECONDARY_GREEN -> {
                tvTagText?.let {
                    it.setBackgroundResource(R.drawable.tag_secodary_green_background)
                    it.setTextColor(context.getColor(R.color.text_base))
                }
                setPaddings(PADDING_HORIZONTAL_20_DP, PADDING_VERTICAL_8_DP)
            }

            STYLE_SECONDARY_GRAY -> {
                tvTagText?.let {
                    it.setBackgroundResource(R.drawable.tag_secondary_gray_background)
                    it.setTextColor(context.getColor(R.color.text_caption_dark))
                }
                setPaddings(PADDING_HORIZONTAL_20_DP, PADDING_VERTICAL_8_DP)
            }

            STYLE_ONBOARDING_YELLOW -> {
                tvTagText?.let {
                    it.setBackgroundResource(R.drawable.tag_onboadring_yellow_background)
                    it.setTextColor(context.getColor(R.color.text_base))
                    it.setTextAppearance(R.style.Text_Base_SplashItem)
                }
                setPaddings(PADDING_HORIZONTAL_20_DP, PADDING_VERTICAL_8_DP)
            }

            STYLE_ONBOARDING_VIOLET -> {
                tvTagText?.let {
                    it.setBackgroundResource(R.drawable.tag_onboarding_violet_background)
                    it.setTextColor(context.getColor(R.color.text_light))
                    it.setTextAppearance(R.style.Text_Base_SplashItem)
                }
                setPaddings(PADDING_HORIZONTAL_20_DP, PADDING_VERTICAL_8_DP)
            }
        }
    }

    private fun setPaddings(horizontal: Int, vertical: Int) {
        tvTagText?.setPadding(
            (horizontal * resources.displayMetrics.density).toInt(),
            (vertical * resources.displayMetrics.density).toInt(),
            (horizontal * resources.displayMetrics.density).toInt(),
            (vertical * resources.displayMetrics.density).toInt(),
        )
    }

    companion object {
        private const val PADDING_HORIZONTAL_12_DP = 12
        private const val PADDING_HORIZONTAL_20_DP = 20
        private const val PADDING_VERTICAL_4_DP = 4
        private const val PADDING_VERTICAL_8_DP = 8

        const val STYLE_PRIMARY_VIOLET = 1
        const val STYLE_PRIMARY_GRAY = 2
        const val STYLE_SECONDARY_GREEN = 3
        const val STYLE_SECONDARY_GRAY = 4
        const val STYLE_ONBOARDING_YELLOW = 5
        const val STYLE_ONBOARDING_VIOLET = 6

        enum class Style {
            PRIMARY_VIOLET,
            PRIMARY_GRAY,
            SECONDARY_GREEN,
            SECONDARY_GRAY,
            ONBOARDING_YELLOW,
            ONBOARDING_VIOLET
        }
    }
}