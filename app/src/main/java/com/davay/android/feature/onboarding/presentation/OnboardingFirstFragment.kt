package com.davay.android.feature.onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.davay.android.R

class OnboardingFirstFragment(private val contentIds: IntArray) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_onboarding_first,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tv_top_title).setText(contentIds[0])
        view.findViewById<ImageView>(R.id.iv_main_image).setImageResource(contentIds[1])
        view.findViewById<TextView>(R.id.tv_bottom_title).setText(contentIds[2])
    }
}