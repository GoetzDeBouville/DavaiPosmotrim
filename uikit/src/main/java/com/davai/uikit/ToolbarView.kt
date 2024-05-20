package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

/**
 * Дефолтно в ToolbarView для ivStartIcon и ivEndIcon установлены изображения ic_arrow_back и
 * ic_heart соответственно.
 * Элементы tvMatchesCounter и ivEndIcon не видимы по дефолту
 */
class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : ConstraintLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
) {
    private var tvTitle: TextView? = null
    private var tvSubtitle: TextView? = null
    private var tvMatchesCounter: TextView? = null
    private var ivStartIcon: ImageView? = null
    private var ivEndIcon: ImageView? = null

    init {
        initViews()
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
            R.styleable.ToolbarView,
            defStyleAttr,
            defStyleRes
        ).apply {
            val subTitleText = getString(R.styleable.ToolbarView_subtitle) ?: ""
            val titleText = getString(R.styleable.ToolbarView_title) ?: ""
            val startIconResId = getResourceId(R.styleable.ToolbarView_start_icon, 0)
            val endIconResId = getResourceId(R.styleable.ToolbarView_end_icon, 0)

            ivEndIcon?.isVisible = getBoolean(R.styleable.ToolbarView_end_icon_is_visible, false)

            setTitleText(titleText)
            setSubtitleText(subTitleText)
            setStartIcon(startIconResId)
            setEndIcon(endIconResId)
        }
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.toolbar_view, this)
        tvTitle = findViewById(R.id.tv_toolbar_title)
        tvSubtitle = findViewById(R.id.tv_toolbar_subtitle)
        tvMatchesCounter = findViewById(R.id.tv_matches_counter)
        ivStartIcon = findViewById(R.id.iv_start_icon)
        ivEndIcon = findViewById(R.id.iv_end_icon)
    }

    /**
     * Назначает текст счетчика совпадений и управляет видимостью
     */
    fun updateMatchesDisplay(numberOfMatches: Int) {
        tvMatchesCounter?.isVisible = when {
            numberOfMatches > 0 -> {
                tvMatchesCounter?.text = numberOfMatches.toString()
                true
            }

            else -> false
        }
    }

    /**
     * Назначает текст для подзаголовка
     */
    fun setSubtitleText(text: String) {
        tvSubtitle?.text = text
    }

    /**
     * Назначает текст для заголовка
     */
    fun setTitleText(text: String) {
        tvTitle?.text = text
    }

    /**
     * Назначает ресурс для иконкм для заголовка
     */
    fun setStartIcon(resId: Int) {
        if (resId != 0) {
            ivStartIcon?.setImageResource(resId)
        }
    }

    /**
     * Назначает ресурс для иконкм для заголовка
     */
    fun setEndIcon(resId: Int) {
        if (resId != 0) {
            ivEndIcon?.setImageResource(resId)
        }
    }

    /**
     * Упроавление видимостью ivEndIcon
     */
    fun showEndIcon() {
        ivEndIcon?.isVisible = true
    }

    /**
     * Упроавление видимостью ivEndIcon
     */
    fun hideEndIcon() {
        ivEndIcon?.isVisible = false
    }

    /**
     * Упроавление видимостью ivEndIcon
     */
    fun showStartIcon() {
        ivStartIcon?.isVisible = true
    }

    /**
     * Упроавление видимостью ivEndIcon
     */
    fun hideStartIcon() {
        ivStartIcon?.isVisible = false
    }

    /**
     * Метод для управления слушателем кликов по элементу ivStartIcon
     */
    fun setStartIconClickListener(listener: () -> Unit) {
        ivStartIcon?.setOnClickListener { listener() }
    }

    /**
     * Метод для управления слушателем кликов по элементу ivEndIcon
     */
    fun setEndIconClickListener(listener: () -> Unit) {
        ivEndIcon?.setOnClickListener { listener() }
    }
}