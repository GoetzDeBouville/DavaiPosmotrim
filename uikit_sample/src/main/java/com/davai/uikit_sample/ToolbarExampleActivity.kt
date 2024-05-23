package com.davai.uikit_sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.ToolbarView

@Suppress("Detekt.MagicNumber", "Detekt.ArgumentListWrapping")
class ToolbarExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_toolbar_example)

        findViewById<ToolbarView>(R.id.toolbarView).apply {
            setSubtitleText("Session 828fuYT")
            setTitleText("3 September again")
            showStartIcon()
            setStartIcon(com.davai.uikit.R.drawable.ic_arrow_back)
            setEndIcon(com.davai.uikit.R.drawable.ic_heart)
            showEndIcon()
            setStartIconClickListener {
                Toast.makeText(
                    this@ToolbarExampleActivity, "Start icon clicked", Toast.LENGTH_SHORT
                ).show()
            }
            setEndIconClickListener {
                Toast.makeText(this@ToolbarExampleActivity, "End icon clicked", Toast.LENGTH_SHORT)
                    .show()
            }
            addStatusBarSpacer()
        }

        findViewById<ToolbarView>(R.id.toolbarView2).apply {
            updateMatchesDisplay(5)
            showEndIcon()
            showMatchesCounter()
            setEndIcon(com.davai.uikit.R.drawable.ic_heart)
            setStartIcon(com.davai.uikit.R.drawable.ic_cross)
            setSubtitleText("Session qwrt12ew")
            showStartIcon()
        }

        findViewById<ToolbarView>(R.id.toolbarView3).apply {
            showEndIcon()
            setEndIcon(com.davai.uikit.R.drawable.ic_cross)
            setStartIcon(com.davai.uikit.R.drawable.ic_heart)
            showStartIcon()
        }
    }
}