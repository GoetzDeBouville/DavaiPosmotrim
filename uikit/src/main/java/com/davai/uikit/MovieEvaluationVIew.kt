package com.davai.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat

class MovieEvaluationVIew @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var tvRateNumber: TextView? = null
    private var tvNumberRates: TextView? = null
    private var tvRateService: TextView? = null
    private var body: LinearLayout? = null

    init {
        initViews()
        applyAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.movie_evaluation_view, this, true)
        tvRateNumber = findViewById(R.id.tv_rate_number)
        tvNumberRates = findViewById(R.id.tv_number_rates)
        tvRateService = findViewById(R.id.tv_rate_service)
        body = findViewById(R.id.ll_evaluation_body)
    }

    private fun applyAttributes(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MovieEvaluationView,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                val rateNum = getFloat(R.styleable.MovieEvaluationView_rate_num, 0.0f)
                val numberOfRates = getInt(R.styleable.MovieEvaluationView_number_of_rates, 0)

                setEvaluationBackground(rateNum)
                setNuberRates(numberOfRates)
                tvRateNumber?.text = rateNum.toString()
                tvRateService?.text = getString(R.styleable.MovieEvaluationView_rate_service)
            } finally {
                recycle()
            }
        }
    }

    @Suppress("Detekt.MagicNumber")
    private fun setEvaluationBackground(rate: Float) {
        val color = ContextCompat.getColor(
            context,
            when {
                rate < 5 -> R.color.error
                rate >= 7 -> R.color.done
                else -> R.color.attention
            }
        )
        body?.backgroundTintList = ColorStateList.valueOf(color)
    }

    @Suppress("Detekt.ImplicitDefaultLocale")
    private fun setNuberRates(numberOfRates: Int) {
        val formattedNumber = String.format("%,d", numberOfRates).replace(",", " ")
        tvNumberRates?.text =
            resources.getQuantityString(R.plurals.rate_nums, numberOfRates, formattedNumber)
    }
}