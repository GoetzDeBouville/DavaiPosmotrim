package com.davai.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView

class PrimaryButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var buttonText: String
    private var buttonEnabled: Boolean
    private var buttonLoading: Boolean
    private val frame: View
    private val textView: TextView
    private val progressBar: ProgressBar
    private var buttonViewType: Int

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.PrimaryButtonView,
            defStyleAttr,
            defStyleRes
        )
        buttonText = typedArray.getString(R.styleable.PrimaryButtonView_button_text) ?: ""
        buttonEnabled = typedArray.getBoolean(R.styleable.PrimaryButtonView_button_enabled, true)
        buttonLoading = typedArray.getBoolean(R.styleable.PrimaryButtonView_button_loading, false)
        buttonViewType = typedArray.getInt(R.styleable.PrimaryButtonView_button_view_type, 1)
        typedArray.recycle()
        LayoutInflater.from(context).inflate(R.layout.primary_button_view, this)
        frame = findViewById<View>(R.id.frame)
        textView = findViewById<TextView>(R.id.text_view)
        progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        setButtonViewType(ButtonViewType.getButtonViewType(buttonViewType))
    }

    fun setButtonText(text: String) {
        buttonText = text
        when (buttonViewType) {
            1 -> {
                if (progressBar.visibility != View.VISIBLE) {
                    textView.text = text
                }
            }

            2 -> {
                if (progressBar.visibility != View.VISIBLE && textView.isEnabled) {
                    textView.text = text
                }
            }
        }
    }

    fun setButtonEnabled(isEnabled: Boolean) {
        buttonEnabled = isEnabled
        textView.isEnabled = isEnabled
        if (buttonViewType == 2) {
            if (textView.isEnabled) {
                textView.text = buttonText
            } else {
                textView.text = ""
            }
        }
    }

    fun setLoading(loading: Boolean) {
        buttonLoading = loading
        if (loading) {
            textView.text = ""
            progressBar.visibility = View.VISIBLE
        } else {
            textView.text = buttonText
            progressBar.visibility = View.GONE
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return when (ev?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                return buttonEnabled && !buttonLoading
            }

            else -> {
                false
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                when (buttonViewType) {
                    1 -> {
                        frame.visibility = View.VISIBLE
                    }

                    2 -> {
                        textView.setTextColor(
                            resources.getColor(
                                R.color.text_caption_dark,
                                context.theme
                            )
                        )
                    }
                }
                true
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                when (buttonViewType) {
                    1 -> {
                        frame.visibility = View.INVISIBLE
                    }

                    2 -> {
                        textView.setTextColor(resources.getColor(R.color.text_base, context.theme))
                    }
                }
                performClick()
                true
            }

            else -> {
                super.onTouchEvent(event)
            }
        }
    }

    fun setButtonViewType(type: ButtonViewType) {
        when (type) {
            ButtonViewType.PRIMARY -> {
                buttonViewType = 1
            }

            ButtonViewType.SECONDARY -> {
                buttonViewType = 2
                frame.visibility = View.GONE
                textView.setTextAppearance(R.style.Text_Base_Button_Bold)
                textView.setTextColor(resources.getColor(R.color.text_base, context.theme))
                textView.setBackgroundColor(
                    resources.getColor(
                        android.R.color.transparent,
                        context.theme
                    )
                )
            }
        }
        setButtonText(buttonText)
        setButtonEnabled(buttonEnabled)
        setLoading(buttonLoading)
    }

    enum class ButtonViewType {
        PRIMARY,
        SECONDARY;

        companion object {
            fun getButtonViewType(typeInt: Int): ButtonViewType =
                if (typeInt == 1) {
                    PRIMARY
                } else {
                    SECONDARY
                }

        }
    }
}
