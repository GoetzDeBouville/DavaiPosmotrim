package com.davai.uikit_sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit_sample.databinding.ActivityMainBinding

@Suppress("Detekt.LongMethod")
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
            btnToDvToolbar,
            btnToDvTags,
            toMsb
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

                btnToDvBanner -> startActivity(
                    Intent(
                        this@MainActivity,
                        BannerViewExample::class.java
                    )
                )

                btnToDvButton -> startActivity(
                    Intent(
                        this@MainActivity,
                        ButtonViewExampleActivity::class.java
                    )
                )

                btnToDvFilm -> startActivity(
                    Intent(
                        this@MainActivity,
                        MoovieCardViewExampleActivity::class.java
                    )
                )

                btnToDvSession -> startActivity(Intent(this@MainActivity, SessionExample::class.java))

                btnToDvToolbar -> startActivity(Intent(this@MainActivity, ToolbarExampleActivity::class.java))

                btnToDvMovieSelection -> startActivity(
                    Intent(
                        this@MainActivity,
                        MovieSelectionExampleActivity::class.java
                    )
                )

                toMsb -> startActivity(Intent(this@MainActivity, MainScreenButtonViewExample::class.java))

                btnToDvTags -> startActivity(
                    Intent(
                        this@MainActivity,
                        TagViewExample::class.java
                    )
                )
            }
        }
    }
}