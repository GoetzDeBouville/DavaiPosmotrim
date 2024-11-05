package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton

/**
 * ErrorScreenView необходима только для того чтобы избежать дублирования кода в логике обработки
 * отображения ошибок в UI
 */
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
        LayoutInflater.from(context)
            .inflate(R.layout.layout_error_message, this, true)
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