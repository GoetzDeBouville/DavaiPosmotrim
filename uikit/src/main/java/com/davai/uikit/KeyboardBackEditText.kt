package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText

/**
Поле ввода текста, обрабатывающее нажатие кнопки назад.
 */
class KeyboardBackEditText : AppCompatEditText {

    var buttonBackHandler: (() -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && buttonBackHandler != null) {
            buttonBackHandler!!.invoke()
            false
        } else {
            super.onKeyPreIme(keyCode, event)
        }
    }
}