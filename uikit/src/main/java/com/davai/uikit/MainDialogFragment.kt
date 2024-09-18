package com.davai.uikit

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.davai.uikit.databinding.LayoutCustomDialogBinding
import com.davai.uikit.extensions.applyBlurEffect
import com.davai.uikit.extensions.clearBlurEffect
import com.davai.util.setOnDebouncedClickListener

class MainDialogFragment : DialogFragment() {

    private var title: String? = null
    private var message: String? = null
    private var yesAction: (() -> Unit)? = null
    private var noAction: (() -> Unit)? = null
    private var showConfirmBlock: Boolean? = null

    private var _binding: LayoutCustomDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainDialogViewModel by lazy {
        ViewModelProvider(requireActivity())[MainDialogViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.session_card_background)
        _binding = LayoutCustomDialogBinding.inflate(inflater, container, false)
        activity?.window?.decorView?.applyBlurEffect()
        viewModel.title = title
        viewModel.message = message
        viewModel.yesAction = yesAction ?: viewModel.yesAction
        viewModel.noAction = noAction ?: viewModel.noAction
        showConfirmBlock?.let { viewModel.showConfirmBlock = it }

        initViews()
        subscribe()

        return binding.root
    }

    private fun initViews() = with(binding) {
        tvDialogTitle.text = viewModel.title
        tvDialogMessage.text = viewModel.message
        if (viewModel.showConfirmBlock) {
            showConfirmButton()
        }
    }

    private fun showConfirmButton() = with(binding) {
        llTwoButtonsBlock.isVisible = viewModel.showConfirmBlock.not()

        progressButtonItem.root.isVisible = viewModel.showConfirmBlock

        progressButtonItem.progressButton.text = getString(R.string.dialog_confirm_text_ok)
        launchProgressButtonAnimation()
    }

    private fun subscribe() = with(binding) {
        btnYes.setOnDebouncedClickListener(lifecycleScope, DEFAULT_DELAY_1000) {
            viewModel.yesAction?.invoke()
            dialog?.dismiss()
            activity?.window?.decorView?.clearBlurEffect()
        }

        btnNo.setOnDebouncedClickListener(lifecycleScope, DEFAULT_DELAY_1000) {
            viewModel.noAction?.invoke()
            dialog?.dismiss()
            activity?.window?.decorView?.clearBlurEffect()
        }

        progressButtonItem.root.setOnDebouncedClickListener(lifecycleScope, DEFAULT_DELAY_1000) {
            viewModel.yesAction?.invoke()
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
            it.animateProgress(lifecycleScope) {
                dismiss()
                viewModel.yesAction?.invoke()
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        activity?.window?.decorView?.clearBlurEffect()
        viewModel.noAction?.invoke()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activity?.window?.decorView?.clearBlurEffect()
        _binding = null
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            viewModel.title = savedInstanceState.getString(KEY_TITLE)
            viewModel.message = savedInstanceState.getString(KEY_MESSAGE)
            initViews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TITLE, viewModel.title)
        outState.putString(KEY_MESSAGE, viewModel.message)
    }

    companion object {
        private const val SCREEN_WIDTH = 0.9
        private const val DEFAULT_DELAY_1000 = 1000L
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_MESSAGE = "KEY_MESSAGE"

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
