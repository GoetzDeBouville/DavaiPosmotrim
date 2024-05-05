package com.davai.uikit_sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        clickListeners()
    }

    private fun clickListeners() = with(binding) {
        listOf(
            btnToMoviewEvalution,
            btnToDvBanner,
            btnToDvButton,
            btnToDvFilm,
            btnToDvSession,
            btnToDvMovieSelection,
            btnToDvToolbar
        ).forEach {
            it.setOnClickListener(onClickListener())
        }
    }

    private fun onClickListener() = View.OnClickListener {
        with(binding) {
            when (it) {
                btnToMoviewEvalution -> startActivity(
                    Intent(
                        this@MainActivity,
                        MovieEvaluationExample::class.java
                    )
                )

                btnToDvBanner -> Toast.makeText(
                    this@MainActivity,
                    "ToDvBanner",
                    Toast.LENGTH_SHORT
                )
                    .show()

                btnToDvButton -> startActivity(
                    Intent(
                        this@MainActivity,
                        ButtonViewExampleActivity::class.java
                    )
                )

                btnToDvFilm -> Toast.makeText(
                    this@MainActivity,
                    "ToDvFilm",
                    Toast.LENGTH_SHORT
                )
                    .show()

                btnToDvSession -> Toast.makeText(
                    this@MainActivity,
                    "ToDvSession",
                    Toast.LENGTH_SHORT
                ).show()

                btnToDvToolbar -> Toast.makeText(
                    this@MainActivity,
                    "ToDvToolbar",
                    Toast.LENGTH_SHORT
                ).show()

                btnToDvMovieSelection -> startActivity(
                    Intent(
                        this@MainActivity,
                        MovieSelectionExampleActivity::class.java
                    )
                )
            }
        }
    }
}