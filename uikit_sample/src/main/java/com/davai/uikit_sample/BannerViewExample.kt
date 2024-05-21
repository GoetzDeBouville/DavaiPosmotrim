package com.davai.uikit_sample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.BannerView

class BannerViewExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_banner_view_example)

        val bannerView = findViewById<BannerView>(R.id.banner_view)

        bannerView.setState(BannerView.SUCCESS)
        bannerView.setBannerText(getString(com.davai.uikit.R.string.banner_success))

        val bannerView2 = findViewById<BannerView>(R.id.banner_view2)
        bannerView2.setState(BannerView.ATTENTION)
        bannerView2.setBannerText(getString(com.davai.uikit.R.string.banner_attention))

        val bannerView3 = findViewById<BannerView>(R.id.banner_view3)
        bannerView3.setState(BannerView.INFO)
        bannerView3.setBannerText(getString(com.davai.uikit.R.string.banner_success))
    }
}