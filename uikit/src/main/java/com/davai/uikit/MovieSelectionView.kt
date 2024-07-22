package com.davai.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
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
    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progress_bar)
    }
    private val gradient: View by lazy {
        findViewById(R.id.view_gradient)
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
        if (url.isEmpty()) {
            ivThemeCover.load(R.drawable.placeholder_error_332dp) {
                transformations(RoundedCornersTransformation())
                    .crossfade(true)
            }
        } else {
            ivThemeCover.load(url) {
                listener(
                    onStart = {
                        progressBar.isGone = false
                        gradient.isGone = true
                    },
                    onSuccess = { _, result ->
                        progressBar.isGone = true
                        gradient.isGone = false
                        ivThemeCover.setImageDrawable(result.drawable)
                    },
                    onError = { _, _ ->
                        progressBar.isGone = true
                        gradient.isGone = false
                        ivThemeCover.setImageResource(R.drawable.placeholder_error_332dp)
                    }
                ).scale(Scale.FIT)
                transformations(
                    RoundedCornersTransformation()
                ).crossfade(true)
            }
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
