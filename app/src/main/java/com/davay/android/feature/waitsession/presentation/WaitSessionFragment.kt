package com.davay.android.feature.waitsession.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentWaitSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.waitsession.di.DaggerWaitSessionFragmentComponent
import com.davay.android.feature.waitsession.domain.User
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class WaitSessionFragment : BaseFragment<FragmentWaitSessionBinding, WaitSessionViewModel>(
    FragmentWaitSessionBinding::inflate
) {
    override val viewModel: WaitSessionViewModel by injectViewModel<WaitSessionViewModel>()
    private var userAdapter: UserAdapter? = null

    override fun diComponent(): ScreenComponent = DaggerWaitSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        userAdapter?.itemList?.addAll(
            listOf(
                User("1", "Дима"),
                User("2", "Петя"),
                User("3", "Женя"),
                User("4", "Леша"),
                User("5", "Катя"),
                User("6", "Коля"),
                User("7", "Елена"),
            )
        )

        binding.copyButton.setOnClickListener {
            val code = binding.tvCode.text.toString()
            copyTextToClipboard(code)
        }

        binding.sendButton.setOnClickListener {
            val code = binding.tvCode.text.toString()
            sendCode(code)
        }
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
        startActivity(shareIntent)
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

        val itemDecoration = FlexboxItemDecoration(context)
        val divider = ContextCompat.getDrawable(requireContext(), com.davai.uikit.R.drawable.recycler_view_divider)
        itemDecoration.setDrawable(divider)

        binding.rvUser.addItemDecoration(itemDecoration)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        userAdapter = null
    }
}