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
import java.util.Locale

class MovieEvaluationVIew @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
) {
    private val tvRateNumber: TextView by lazy {
        findViewById(R.id.tv_rate_number)
    }
    private val tvNumberRates: TextView by lazy {
        findViewById(R.id.tv_number_rates)
    }
    private val tvRateService: TextView by lazy {
        findViewById(R.id.tv_rate_service)
    }
    private val body: LinearLayout by lazy {
        findViewById(R.id.ll_evaluation_body)
    }

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
        LayoutInflater.from(context).inflate(
            R.layout.movie_evaluation_view,
            this,
            true
        )
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
                val rateService = getString(R.styleable.MovieEvaluationView_rate_service).toString()

                setNumberOfRatesString(numberOfRates)
                setRateNum(rateNum)
                setServiceNameString(rateService)
            } finally {
                recycle()
            }
        }
    }

    fun setRateNum(rateNum: Float) {
        tvRateNumber.text = rateNum.toString()
        setItemBackground(rateNum)
    }

    private fun setItemBackground(rate: Float) {
        val color = ContextCompat.getColor(
            context,
            when {
                rate < BAD_RATE -> R.color.error
                rate >= GOOD_RATE -> R.color.done
                else -> R.color.attention
            }
        )
        body.backgroundTintList = ColorStateList.valueOf(color)
    }

    /**
     * Метод setNumberOfRatesString принимает количество оценок и возвращает строку с разделением
     * числа по разрядам и плюралом слова "оценка"
     */
    fun setNumberOfRatesString(numberOfRates: Int) {
        val formattedNumber = String.format(Locale.ROOT, "%,d", numberOfRates).replace(",", " ")
        tvNumberRates.text =
            resources.getQuantityString(R.plurals.rate_nums, numberOfRates, formattedNumber)
    }

    /**
     * Метод setServiceNameString принимает строку с названием сервиса в виде "Рейтинг IMDb"
     */
    fun setServiceNameString(rateService: String) {
        tvRateService.text = rateService
    }

    private companion object {
        const val BAD_RATE = 5
        const val GOOD_RATE = 7
    }
}