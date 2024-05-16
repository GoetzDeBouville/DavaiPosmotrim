package com.davai.uikit_sample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.davai.uikit.DvBannerView

class DvBannerViewExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dv_banner_view_example)

        val bannerView = findViewById<DvBannerView>(R.id.banner_view)

        bannerView.setBannerIcon(com.davai.uikit.R.drawable.ic_success)
        bannerView.setBannerText(getString(com.davai.uikit.R.string.dv_banner_success))
        bannerView.setBannerBackgroundColor(getColor(com.davai.uikit.R.color.info))
    }
}