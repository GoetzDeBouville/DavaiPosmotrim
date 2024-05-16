package com.davai.uikit_sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.ToolbarView

@Suppress("Detekt.MagicNumber")
class ToolbarExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_toolbar_example)

        findViewById<ToolbarView>(R.id.toolbarView).apply {
            setSubtitleText("Session 828fuYT")
            setTitleText("3 September again")
            showEndIcon()
            setStartIconClickListener {
                Toast.makeText(
                    this@ToolbarExampleActivity,
                    "Start icon clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setEndIconClickListener {
                Toast.makeText(this@ToolbarExampleActivity, "End icon clicked", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        findViewById<ToolbarView>(R.id.toolbarView2).apply {
            updateMatchesDisplay(5)
            showEndIcon()
        }

        findViewById<ToolbarView>(R.id.toolbarView3).apply {
            showEndIcon()
            setEndIcon(R.drawable.ic_launcher_foreground)
            setStartIcon(R.drawable.ic_launcher_foreground)
        }

        findViewById<ToolbarView>(R.id.toolbarView4).apply {
            hideEndIcon()
            setEndIcon(R.drawable.ic_launcher_foreground)
            setStartIcon(R.drawable.ic_launcher_foreground)
        }
    }
}