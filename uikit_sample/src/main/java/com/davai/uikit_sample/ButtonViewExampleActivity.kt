package com.davai.uikit_sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.PrimaryButtonView
import com.davai.uikit.SecondaryButtonView

class ButtonViewExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_button_view_example)

        val button1 = findViewById<PrimaryButtonView>(R.id.button_1)
        val button5 = findViewById<PrimaryButtonView>(R.id.button_5)
        val button6 = findViewById<PrimaryButtonView>(R.id.button_6)
        val buttonSec2 = findViewById<SecondaryButtonView>(R.id.button_sec_2)
        val buttonSec3 = findViewById<SecondaryButtonView>(R.id.button_sec_3)
        val buttonSec4 = findViewById<SecondaryButtonView>(R.id.button_sec_4)

        button5.setButtonText("Какой-то текст")
        button5.setButtonEnabled(false)
        button6.setLoading(true)
        button6.setButtonText("Какой-то текст")

        buttonSec2.setButtonText("Текст из кода")
        buttonSec3.setButtonText("Текст из кода")
        buttonSec3.setButtonEnabled(false)
        buttonSec3.setButtonText("Текст из кода 2")
        buttonSec4.setLoading(true)
        buttonSec4.setButtonText("не должно быть видно")

        button1.setOnClickListener {
            startActivity(
                Intent(
                    this@ButtonViewExampleActivity,
                    MovieSelectionExampleActivity::class.java
                )
            )
        }

        button5.setOnClickListener {
            startActivity(
                Intent(
                    this@ButtonViewExampleActivity,
                    MovieSelectionExampleActivity::class.java
                )
            )
        }

        button5.setOnClickListener {
            startActivity(
                Intent(
                    this@ButtonViewExampleActivity,
                    MovieSelectionExampleActivity::class.java
                )
            )
        }

        buttonSec2.setOnClickListener {
            startActivity(
                Intent(
                    this@ButtonViewExampleActivity,
                    MovieSelectionExampleActivity::class.java
                )
            )
        }
    }
}