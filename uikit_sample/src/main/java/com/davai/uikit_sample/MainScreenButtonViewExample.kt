package com.davai.uikit_sample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davai.uikit.MainScreenButtonView

class MainScreenButtonViewExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen_button_view_example)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<MainScreenButtonView>(R.id.join).setState(MainScreenButtonView.JOIN)
        findViewById<MainScreenButtonView>(R.id.favorite).setState(MainScreenButtonView.FAVORITE)
        findViewById<MainScreenButtonView>(R.id.create).setState(MainScreenButtonView.CREATE)
    }
}