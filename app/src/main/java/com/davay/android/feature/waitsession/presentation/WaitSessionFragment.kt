package com.davay.android.feature.waitsession.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.davai.uikit.ButtonView
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentWaitSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.waitsession.di.DaggerWaitSessionFragmentComponent
import com.davay.android.feature.waitsession.presentation.adapter.CustomItemDecorator
import com.davay.android.feature.waitsession.presentation.adapter.UserAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlin.math.roundToInt

class WaitSessionFragment : BaseFragment<FragmentWaitSessionBinding, WaitSessionViewModel>(
    FragmentWaitSessionBinding::inflate
) {
    override val viewModel: WaitSessionViewModel by injectViewModel<WaitSessionViewModel>()
    private var userAdapter: UserAdapter? = null
    private var sendButton: ButtonView? = null
    private var launcher: ActivityResultLauncher<Intent>? = null

    override fun diComponent(): ScreenComponent = DaggerWaitSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { _ ->
            sendButton?.setButtonEnabled(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.addStatusBarSpacer()
        sendButton = binding.sendButton

        initRecycler()
        userAdapter?.itemList?.addAll(
            listOf("Дима", "Петя", "Женя", "Леша", "Катя", "Коля", "Елена")
        )

        binding.llCopyButton.setOnClickListener {
            val code = binding.tvCode.text.toString()
            copyTextToClipboard(code)
        }

        sendButton?.setOnClickListener {
            val code = binding.tvCode.text.toString()
            if (it.isEnabled) {
                sendCode(code)
                sendButton?.setButtonEnabled(false)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        launcher = null
    }

    private fun copyTextToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            ContextCompat.getString(requireContext(), R.string.wait_session_copy_button_label),
            text
        )
        clipboard.setPrimaryClip(clip)

        Toast.makeText(context, R.string.wait_session_copy_button_toast_text, Toast.LENGTH_SHORT)
            .show()
    }

    private fun sendCode(text: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        launcher?.launch(shareIntent)
    }

    private fun initRecycler() {
        userAdapter = UserAdapter()
        binding.rvUser.adapter = userAdapter
        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }

        binding.rvUser.layoutManager = layoutManager
        val spaceBetweenItems = convertPxToDp(requireContext(), SPACING_BETWEEN_RV_ITEMS)
        val itemDecoration = CustomItemDecorator(spaceBetweenItems)
        binding.rvUser.addItemDecoration(itemDecoration)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        userAdapter = null
    }

    private fun convertPxToDp(context: Context, px: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return (px * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    companion object {
        private const val SPACING_BETWEEN_RV_ITEMS = 8
    }
}