package com.davai.uikit_sample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.PrimaryButtonView

class ButtonViewExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_button_view_example)

        val button5 = findViewById<PrimaryButtonView>(R.id.button_5)
        val button6 = findViewById<PrimaryButtonView>(R.id.button_6)

        button5.setButtonText("Какой-то текст")
        button5.setButtonEnabled(false)
        button6.setLoading(true)
        button6.setButtonText("Какой-то текст")
    }
}