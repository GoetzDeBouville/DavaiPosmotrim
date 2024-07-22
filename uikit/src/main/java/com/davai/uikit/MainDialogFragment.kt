package com.davai.uikit

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.davai.uikit.databinding.LayoutCustomDialogBinding
import com.davai.uikit.extensions.applyBlurEffect
import com.davai.uikit.extensions.clearBlurEffect

class MainDialogFragment : DialogFragment() {

    private var _binding: LayoutCustomDialogBinding? = null
    private val binding get() = _binding!!
    private var viewModel: MainDialogViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.session_card_background)
        _binding = LayoutCustomDialogBinding.inflate(inflater, container, false)
        activity?.window?.decorView?.applyBlurEffect()
        viewModel = ViewModelProvider(requireActivity())[MainDialogViewModel::class.java]

        initViews()
        subscribe()

        return binding.root
    }

    private fun initViews() {
        with(binding) {
            tvDialogTitle.text = viewModel?.title
            tvDialogMessage.text = viewModel?.message
        }
    }

    private fun subscribe() {
        with(binding) {
            btnYes.setOnClickListener {
                viewModel?.yesAction?.invoke()
                dialog?.dismiss()
                activity?.window?.decorView?.clearBlurEffect()
            }

            btnNo.setOnClickListener {
                viewModel?.noAction?.invoke()
                dialog?.dismiss()
                activity?.window?.decorView?.clearBlurEffect()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * SCREEN_WIDTH).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        activity?.window?.decorView?.clearBlurEffect()
        viewModel?.noAction?.invoke()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activity?.window?.decorView?.clearBlurEffect()
        _binding = null
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            viewModel?.title = savedInstanceState.getString(KEY_TITLE)
            viewModel?.message = savedInstanceState.getString(KEY_MESSAGE)
            initViews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TITLE, viewModel?.title)
        outState.putString(KEY_MESSAGE, viewModel?.message)
    }

    companion object {
        private const val SCREEN_WIDTH = 0.9
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_MESSAGE = "KEY_MESSAGE"

        fun newInstance(
            title: String,
            message: String,
            yesAction: (() -> Unit)? = null,
            noAction: (() -> Unit)? = null
        ): MainDialogFragment {
            val dialog = MainDialogFragment()
            val viewModel =
                ViewModelProvider(dialog.requireActivity())[MainDialogViewModel::class.java]
            viewModel.title = title
            viewModel.message = message
            viewModel.yesAction = yesAction
            viewModel.noAction = noAction
            return dialog
        }
    }
}
