package com.davai.uikit_sample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.TagView

class TagViewExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tag_view_example)

        val thirdButton = findViewById<TagView>(R.id.tag_view3)
        val fourthButton = findViewById<TagView>(R.id.tag_view4)

        thirdButton.setOnClickListener {
            thirdButton.setDisabled()
        }

        fourthButton.setOnClickListener {
            fourthButton.setChosen()
        }
    }
}