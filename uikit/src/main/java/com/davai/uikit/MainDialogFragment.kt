package com.davai.uikit

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.davai.uikit.databinding.LayoutCustomDialogBinding
import com.davai.uikit.extensions.applyBlurEffect
import com.davai.uikit.extensions.clearBlurEffect
import kotlinx.coroutines.launch

class MainDialogFragment : DialogFragment() {

    private var title: String? = null
    private var message: String? = null
    private var yesAction: (() -> Unit)? = null
    private var noAction: (() -> Unit)? = null
    private var showConfirmBlock = false

    private var _binding: LayoutCustomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.session_card_background)
        _binding = LayoutCustomDialogBinding.inflate(inflater, container, false)
        activity?.window?.decorView?.applyBlurEffect()

        initViews()
        subscribe()

        return binding.root
    }

    private fun initViews() = with(binding) {
        tvDialogTitle.text = title
        tvDialogMessage.text = message
        if (showConfirmBlock) {
            showConfirmButton()
        }
    }

    private fun showConfirmButton() = with(binding) {
        llTwoButtonsBlock.isVisible = showConfirmBlock.not()

        topSpacer.isVisible = showConfirmBlock
        bottomSpacer.isVisible = showConfirmBlock
        progressButtonItem.root.isVisible = showConfirmBlock

        progressButtonItem.progressButton.text = getString(R.string.dialog_confirm_text_ok)
        launchProgressButtonAnimation()
    }

    private fun subscribe() = with(binding) {
        btnYes.setOnClickListener {
            yesAction?.invoke()
            dialog?.dismiss()
            activity?.window?.decorView?.clearBlurEffect()
        }

        btnNo.setOnClickListener {
            noAction?.invoke()
            dialog?.dismiss()
            activity?.window?.decorView?.clearBlurEffect()
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * SCREEN_WIDTH).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun launchProgressButtonAnimation() {
        binding.progressButtonItem.progressButton.also {
            lifecycleScope.launch {
                it.animateProgress(this) {
                    dismiss()
                    yesAction?.invoke()
                }
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        activity?.window?.decorView?.clearBlurEffect()
        noAction?.invoke()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activity?.window?.decorView?.clearBlurEffect()
        _binding = null
    }

    companion object {
        private const val SCREEN_WIDTH = 0.9

        fun newInstance(
            title: String,
            message: String,
            showConfirmBlock: Boolean = false,
            yesAction: (() -> Unit)? = null,
            noAction: (() -> Unit)? = null
        ): MainDialogFragment {
            val dialog = MainDialogFragment()
            dialog.title = title
            dialog.message = message
            dialog.showConfirmBlock = showConfirmBlock
            dialog.yesAction = yesAction
            dialog.noAction = noAction
            return dialog
        }
    }
}
