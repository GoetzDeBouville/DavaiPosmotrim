package com.davai.uikit_sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.MovieSelectionView

@Suppress("Detekt.StringLiteralDuplication", "Detekt.ArgumentListWrapping", "Detekt.MaxLineLength")
class MovieSelectionExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var isSelected = false
        setContentView(R.layout.activity_movie_selection_example)
        val movieSelectionTheme = findViewById<MovieSelectionView>(R.id.movie_selection_view)
        with(movieSelectionTheme) {
            this.setThemeCover("https://spotlightonline.co/wp-content/uploads/2017/03/cinema_projector.jpg")
            this.setOnClickListener {
                isSelected = this.switchSelection()
                Toast.makeText(
                    this@MovieSelectionExampleActivity,
                    "isSelected = $isSelected",
                    Toast.LENGTH_SHORT
                ).show()
            }
            this.setThemeTitle("Нестареющая классика")
        }

        val movieSelectionThemeDefault =
            findViewById<MovieSelectionView>(R.id.movie_selection_view_default)

        movieSelectionThemeDefault.apply {
            setThemeCover("https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/b86668ec-b0ac-4519-acfc-4fdca570b711/3840x")
            setThemeTitle("Нестареющая классика")
            this.setOnClickListener {
                isSelected = this.switchSelection()
                Toast.makeText(
                    this@MovieSelectionExampleActivity,
                    "isSelected = $isSelected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val movieSelectionThemeDefault2 =
            findViewById<MovieSelectionView>(R.id.movie_selection_view_default_2)

        with(movieSelectionThemeDefault2) {
            this.setThemeCover("")
            this.setOnClickListener {
                isSelected = this.switchSelection()
                Toast.makeText(
                    this@MovieSelectionExampleActivity,
                    "isSelected = $isSelected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
