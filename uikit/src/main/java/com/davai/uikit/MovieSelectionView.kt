package com.davai.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation

class MovieSelectionView @JvmOverloads constructor(
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
        findViewById(R.id.tv_theme_title)
    }
    private val ivSelectorIcon: ImageView by lazy {
        findViewById(R.id.iv_selector_icon)
    }
    private val ivThemeCover: ImageView by lazy {
        findViewById(R.id.iv_theme_cover)
    }
    private val body: ConstraintLayout by lazy {
        findViewById(R.id.cl_evaluation_body)
    }
    private var isSelected = false
    private var backgroundColor = -1

    init {
        initViews()
        applyAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.movie_selection_view, this)
        body.background?.callback = this
    }

    private fun applyAttributes(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MovieSelectionView,
            defStyleAttr,
            defStyleRes
        ).apply {
            tvTitle.text = getString(R.styleable.MovieSelectionView_theme_title)
            isSelected = getBoolean(R.styleable.MovieSelectionView_is_selected, false)
            setBackgroundColor()
            setBodyTint()
            setSelectorIconVisibility()
        }
    }

    fun setThemeCover(url: String) {
        ivThemeCover.load(url) {
            error(R.drawable.placeholder_theme_112)
                .scale(Scale.FIT)
            placeholder(R.drawable.placeholder_theme_112)
                .scale(Scale.FIT)
            transformations(
                RoundedCornersTransformation()
            )
        }
    }

    fun switchSelection(): Boolean {
        isSelected = !isSelected
        setBackgroundColor()
        setBodyTint()
        setSelectorIconVisibility()
        return isSelected
    }

    private fun setBackgroundColor() {
        backgroundColor = ContextCompat.getColor(
            context, if (isSelected) R.color.secondary_base else R.color.background_white
        )
    }

    private fun setBodyTint() {
        body.backgroundTintList = ColorStateList.valueOf(backgroundColor)
    }

    private fun setSelectorIconVisibility() {
        ivSelectorIcon.isVisible = isSelected
    }

    fun setThemeTitle(title: String) {
        tvTitle.text = title
    }
}
