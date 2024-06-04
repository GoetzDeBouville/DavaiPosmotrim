package com.davai.uikit_sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.davai.uikit.extensions.applyBlurEffect
import com.davai.uikit.extensions.clearBlurEffect

class CustomDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(com.davai.uikit.R.drawable.session_card_background)
        val view = inflater.inflate(com.davai.uikit.R.layout.layout_custom_dialog, container, false)
        activity?.window?.decorView?.applyBlurEffect()
        val btnYes = view.findViewById<Button>(com.davai.uikit.R.id.btn_yes)
        val btnNo = view.findViewById<Button>(com.davai.uikit.R.id.btn_no)

        btnYes.setOnClickListener {
            dialog?.dismiss()
            activity?.window?.decorView?.clearBlurEffect()
        }

        btnNo.setOnClickListener {
            dialog?.dismiss()
            activity?.window?.decorView?.clearBlurEffect()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * SCREEN_WIDTH).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        private const val SCREEN_WIDTH = 0.9
    }
}
