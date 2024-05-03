package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import coil.load
import com.google.android.material.card.MaterialCardView

class MovieCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private var tvMovieTitle: TextView? = null
    private var ivMovieCover: ImageView? = null

    init {
        initViews()
        applyAttributes(context, attrs, defStyleAttr)
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.movie_card_view, this, true)
        tvMovieTitle = findViewById(R.id.tv_movie_title)
        ivMovieCover = findViewById(R.id.iv_movie_cover)
    }

    private fun applyAttributes(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MovieCardView,
            defStyleAttr,
            0
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
        ivMovieCover?.load(url) {
            error(R.drawable.error_img)
            placeholder(R.drawable.placeholder_img)
        }
    }

    fun setMovieTitle(title: String) {
        tvMovieTitle?.text = title
    }
}