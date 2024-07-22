package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.isGone
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation

class SessionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val tvDate: TextView by lazy {
        findViewById(R.id.tv_session_date)
    }
    private val tvCoincidences: TextView by lazy {
        findViewById(R.id.tv_session_coincidences)
    }
    private val tvNamesList: TextView by lazy {
        findViewById(R.id.tv_session_names_list)
    }
    private val ivCover: ImageView by lazy {
        findViewById(R.id.iv_session_cover)
    }
    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progress_bar)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.session_view, this, true)
    }

    fun setDate(date: String) {
        tvDate.text = date
    }

    fun setCoincidences(amount: Int) {
        tvCoincidences.text =
            String.format(resources.getString(R.string.session_coincidences), amount.toString())
    }

    fun setNamesList(names: String) {
        tvNamesList.text = names
    }

    fun setCover(url: String) {
        if (url.isEmpty()) {
            ivCover.load(R.drawable.placeholder_error_332dp) {
                transformations(RoundedCornersTransformation())
                    .crossfade(true)
            }
        } else {
            ivCover.load(url) {
                listener(
                    onStart = {
                        progressBar.isGone = false
                    },
                    onSuccess = { _, result ->
                        progressBar.isGone = true
                        ivCover.setImageDrawable(result.drawable)
                    },
                    onError = { _, _ ->
                        progressBar.isGone = true
                        ivCover.setImageResource(R.drawable.placeholder_error_332dp)
                    }
                ).scale(Scale.FIT)
                transformations(
                    RoundedCornersTransformation()
                ).crossfade(true)
            }
        }
    }
}