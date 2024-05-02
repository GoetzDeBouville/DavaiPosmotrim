package com.davai.uikit_sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.MovieSelectionView

@Suppress("Detekt.StringLiteralDuplication")
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

        with(movieSelectionThemeDefault) {
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
