package com.davay.android.app

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davai.uikit.extensions.applyBlurEffect
import com.davai.uikit.extensions.clearBlurEffect
import com.davay.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
//        val navController = navHostFragment.navController
    }

    /**
     * Добавляет отступ снизу для клавиатуры
     */
    fun setKeyBoardInsets(
        view: View,
        left: Int = 0,
        top: Int = 0,
        right: Int = 0,
        bottom: Int = 0
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            if (insets.isVisible(WindowInsetsCompat.Type.ime())) {
                v.setPadding(left, top, right, bottom + imeHeight)
            } else {
                v.setPadding(left, top, right, bottom)
            }
            insets
        }
    }

    fun applyBlurEffect() {
        binding.root.applyBlurEffect()
    }

    fun clearBlurEffect() {
        binding.root.clearBlurEffect()
    }
}
