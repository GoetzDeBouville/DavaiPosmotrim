package com.davai.uikit_sample

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.davai.uikit.extensions.applyBlurEffect
import com.davai.uikit.extensions.clearBlurEffect
import com.davai.uikit_sample.databinding.ActivityBlurBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Suppress("Detekt.LateinitUsage", "Detekt.MagicNumber")
class BlurActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlurBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlurBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateText()

        binding.btnShowDialog.setOnClickListener {
            buildDialog()
            updateText()
        }
    }

    /**
     * добавлен для демонстрации, что динамические объекты тоже размываются,
     * то есть можно сделать полноценный glass эффект, при необходимости
     */
    private fun updateText() {
        lifecycleScope.launch {
            repeat(200) {
                delay(1000)
                binding.tvSomeText.text = Random.nextInt(100_000).toString()
            }
        }
    }

    private fun buildDialog() {
        applyBlurEffect()

        val alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Title")
            .setMessage("blureffect test")
            .setPositiveButton("yes") { _: DialogInterface, _: Int ->
                clearBlurEffect()
            }
            .setNegativeButton("no") { _: DialogInterface, _: Int ->
                clearBlurEffect()
            }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.setOnCancelListener {
            clearBlurEffect()
        }
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(resources.getColor(R.color.black, null))
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(resources.getColor(com.davai.uikit.R.color.error, null))
    }

    fun applyBlurEffect() {
        binding.root.applyBlurEffect()
    }

    fun clearBlurEffect() {
        binding.root.clearBlurEffect()
    }
}
