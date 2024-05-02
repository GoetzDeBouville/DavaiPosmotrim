package com.davai.uikit_sample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.MovieEvaluationVIew

@Suppress("Detekt.MagicNumber")
class MovieEvaluationExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_evaluation_example)

        val evaluationView = findViewById<MovieEvaluationVIew>(R.id.bad_rate)
        evaluationView.setRateNum(2.3f)
    }
}