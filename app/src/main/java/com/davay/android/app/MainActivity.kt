package com.davay.android.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davay.android.databinding.ActivityMainBinding
import com.davay.android.extensions.applyBlurEffect
import com.davay.android.extensions.clearBlurEffect

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            if (insets.isVisible(WindowInsetsCompat.Type.ime())) {
                v.setPadding(0, 0, 0, imeHeight)
            } else {
                v.setPadding(0, 0, 0, 0)
            }
            insets
        }
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
//        val navController = navHostFragment.navController
    }

    fun applyBlurEffect() {
        binding.root.applyBlurEffect(30f)
    }

    fun clearBlurEffect() {
        binding.root.clearBlurEffect()
    }
}
