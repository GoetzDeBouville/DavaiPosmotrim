package com.davai.uikit_sample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.MovieEvaluationView

@Suppress("Detekt.MagicNumber")
class MovieEvaluationExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_evaluation_example)

        val evaluationView = findViewById<MovieEvaluationView>(R.id.bad_rate)
        evaluationView.setRateNum(2.3f)
        evaluationView.setServiceNameString("Рейтинг Netflix)))")
        evaluationView.setNumberOfRatesString(1_000_501)
    }
}