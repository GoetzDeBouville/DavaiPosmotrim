package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation

class MovieCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val tvMovieTitle: TextView by lazy {
        findViewById(R.id.tv_movie_title)
    }
    private val ivMovieCover: ImageView by lazy {
        findViewById(R.id.iv_movie_cover)
    }
    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progress_bar)
    }

    init {
        inflateView()
        applyAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.movie_card_view, this, true)
    }

    private fun applyAttributes(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MovieCardView,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                val movieTitle = getString(R.styleable.MovieCardView_movie_title) ?: ""
                setMovieTitle(movieTitle)
            } finally {
                recycle()
            }
        }
    }

    fun setMovieCover(url: String) {
        ivMovieCover.load(url) {
            listener(
                onStart = {
                    progressBar.isGone = false
                },
                onSuccess = { _, result ->
                    progressBar.isGone = true
                    ivMovieCover.setImageDrawable(result.drawable)
                },
                onError = { _, _ ->
                    progressBar.isGone = true
                    ivMovieCover.setImageResource(R.drawable.placeholder_error_film_138)
                }
            ).scale(Scale.FIT)
            transformations(
                RoundedCornersTransformation()
            ).crossfade(true)
        }
    }

    fun setMovieTitle(title: String) {
        tvMovieTitle.text = title
    }

    fun setMovieCoverForMatchedSession(url: String) {
        ivMovieCover.load(url) {
            listener(
                onStart = {
                    progressBar.isGone = false
                },
                onSuccess = { _, result ->
                    progressBar.isGone = true
                    ivMovieCover.setImageDrawable(result.drawable)
                    scale(Scale.FIT)
                },
                onError = { _, _ ->
                    progressBar.isGone = true
                    ivMovieCover.setImageResource(R.drawable.placeholder_general_80)
                    tvMovieTitle.background = null
                    tvMovieTitle.setTextColor(ContextCompat.getColor(context, R.color.text_headings))
                }
            ).scale(Scale.FIT)
            transformations(
                RoundedCornersTransformation()
            ).crossfade(true)
        }
    }
}