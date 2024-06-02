package com.davai.uikit_sample

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
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
            btnToDvToolbar,
            btnToDvTags,
            toMsb,
            btnToCustomDialog
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
                
                btnToCustomDialog -> {val message : String? = "Are you sure?"
                    showCustomDialogBox(message)}
            }
        }
    }

    private fun showCustomDialogBox(message: String?) {
    val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(com.davai.uikit.R.layout.layout_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle: TextView = dialog.findViewById(com.davai.uikit.R.id.tv_dialog_title)
        val tvMessage: TextView = dialog.findViewById(com.davai.uikit.R.id.tv_dialog_message)
        val btnYes: Button = dialog.findViewById(com.davai.uikit.R.id.btn_yes)
        val btnNo: Button = dialog.findViewById(com.davai.uikit.R.id.btn_no)
        tvMessage.text = message
    }
}