package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

/**
 * Дефолтные значения в ToolbarView для ivStartIcon, ivEndIcon и tvMatchesCounter не назначены
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
    private val tvTitle: TextView by lazy {
        findViewById(R.id.tv_toolbar_title)
    }
    private val tvSubtitle: TextView by lazy {
        findViewById(R.id.tv_toolbar_subtitle)
    }
    private val tvMatchesCounter: TextView by lazy {
        findViewById(R.id.tv_matches_counter)
    }
    private val ivStartIcon: ImageView by lazy {
        findViewById(R.id.iv_start_icon)
    }
    private val ivEndIcon: ImageView by lazy {
        findViewById(R.id.iv_end_icon)
    }
    private val topSpace: Space by lazy {
        findViewById(R.id.top_space)
    }

    init {
        inflateView()
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

            ivEndIcon.isVisible = getBoolean(R.styleable.ToolbarView_end_icon_is_visible, false)
            ivStartIcon.isVisible =
                getBoolean(R.styleable.ToolbarView_start_icon_is_visible, false)

            setTitleText(titleText)
            setSubtitleText(subTitleText)
            setStartIcon(startIconResId)
            setEndIcon(endIconResId)
        }
    }

    private fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.toolbar_view, this)
    }

    /**
     * Назначает текст счетчика совпадений и управляет видимостью
     */
    fun updateMatchesDisplay(numberOfMatches: Int) {
        tvMatchesCounter.text = numberOfMatches.toString()
    }

    /**
     * Упроавление видимостью счетчиком
     */
    fun showMatchesCounter() {
        tvMatchesCounter.isVisible = true
    }

    /**
     * Упроавление видимостью счетчиком
     */
    fun hideMatchesCounter() {
        tvMatchesCounter.isVisible = false
    }

    /**
     * Назначает текст для подзаголовка
     */
    fun setSubtitleText(text: String) {
        tvSubtitle.text = text
    }

    /**
     * Назначает текст для заголовка
     */
    fun setTitleText(text: String) {
        tvTitle.text = text
    }

    /**
     * Назначает ресурс для StartIcon
     */
    fun setStartIcon(resId: Int) {
        if (resId != 0) {
            ivStartIcon.setImageResource(resId)
        }
    }

    /**
     * Назначает ресурс для EndIcon
     */
    fun setEndIcon(resId: Int) {
        if (resId != 0) {
            ivEndIcon.setImageResource(resId)
        }
    }

    /**
     * Упроавление видимостью ivEndIcon
     */
    fun showEndIcon() {
        ivEndIcon.isVisible = true
    }

    /**
     * Упроавление видимостью ivEndIcon
     */
    fun hideEndIcon() {
        ivEndIcon.isVisible = false
    }

    /**
     * Упроавление видимостью ivStartIcon
     */
    fun showStartIcon() {
        ivStartIcon.isVisible = true
    }

    /**
     * Упроавление видимостью ivStartIcon
     */
    fun hideStartIcon() {
        ivStartIcon.isVisible = false
    }

    /**
     * Метод для управления слушателем кликов по элементу ivStartIcon
     */
    fun setStartIconClickListener(listener: () -> Unit) {
        ivStartIcon.setOnClickListener { listener() }
    }

    /**
     * Метод для управления слушателем кликов по элементу ivEndIcon
     */
    fun setEndIconClickListener(listener: () -> Unit) {
        ivEndIcon.setOnClickListener { listener() }
    }

    /**
     * Добавляет spacer в высоту статус бара
     */
    fun addStatusBarSpacer() {
        var statusBarHeight = 0
        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            applySpaceHeight(statusBarHeight)
            insets
        }
        requestApplyInsets()
    }

    private fun applySpaceHeight(statusBarHeight: Int) {
        topSpace.let {
            val layoutParams = it.layoutParams
            layoutParams.height = statusBarHeight
            it.layoutParams = layoutParams
        }
    }

    /**
     * Удаляет спэйсер в статус баре
     * ! дефолтно в статус баре спэйсер нулевого размера
     */
    fun removeSpacerOnStatusBar() {
        topSpace.visibility = View.GONE
    }
}