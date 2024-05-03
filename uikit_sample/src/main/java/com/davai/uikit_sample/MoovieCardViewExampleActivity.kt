package com.davai.uikit_sample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davai.uikit.MovieCardView

class MoovieCardViewExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_moovie_card_view_example)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mcv1 = findViewById<MovieCardView>(R.id.mcv_1)

        mcv1.setMovieCover("https://avatars.mds.yandex.net/get-kinopoisk-image/10835644/393a5026-4ac2-476e-8e2b-467c71e5d6bf/3840x")
        mcv1.setMovieTitle("Perfect Days")

        val mcv2 = findViewById<MovieCardView>(R.id.mcv_2)
        mcv2.setMovieCover("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/4d5ee1d6-8bbc-4de8-b94b-7429ac8257da/3840x")
        mcv2.setMovieTitle("Изгнание")
    }
}