package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class ErrorScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val tvErrorTitle: TextView by lazy { findViewById(R.id.tv_error_title) }
    private val tvErrorDescription: TextView by lazy { findViewById(R.id.tv_error_description) }
    private val ivErrorImg: ImageView by lazy { findViewById(R.id.iv_error_image) }
    private val button: MaterialButton by lazy { findViewById(R.id.mbtn_error_button) }

    init {
        inflateView()
        applyAttributes(
            context,
            attrs,
            defStyleAttr,
            defStyleRes
        )
    }

    private fun inflateView() {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_error_message, this, true)
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
                setErrorTitle(getString(R.styleable.ErrorScreenView_error_title) ?: "")
                setErrorDescription(getString(R.styleable.ErrorScreenView_error_description) ?: "")
                setButtonText(getString(R.styleable.ErrorScreenView_error_button_text) ?: "")
                setErrorImage(R.styleable.ErrorScreenView_error_image_id)
            } finally {
                recycle()
            }
        }
    }

    fun setErrorImage(resId: Int) {
        ivErrorImg.setImageResource(resId)
    }

    fun setErrorTitle(text: String) {
        tvErrorTitle.text = text
    }

    fun setErrorDescription(text: String) {
        tvErrorDescription.text = text
    }

    fun setButtonClickListener(action: () -> Unit) {
        button.setOnClickListener {
            action.invoke()
        }
    }

    fun setButtonText(text: String) {
        if (text.isEmpty()) {
            button.visibility = View.GONE
        } else {
            button.text = text
        }
    }
}