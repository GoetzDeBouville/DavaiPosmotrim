package com.davay.android.feature.sessionlist.presentation

import android.os.Bundle
import android.view.View
import com.davai.extensions.dpToPx
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSessionListBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.sessionlist.di.DaggerSessionListFragmentComponent
import com.davay.android.feature.sessionlist.presentation.adapter.CustomItemDecorator
import com.davay.android.feature.sessionlist.presentation.adapter.UserAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SessionListFragment : BaseFragment<FragmentSessionListBinding, SessionListViewModel>(
    FragmentSessionListBinding::inflate
) {
    override val viewModel: SessionListViewModel by injectViewModel<SessionListViewModel>()
    private val userAdapter = UserAdapter()
    private var etCode: String? = null

    override fun diComponent(): ScreenComponent = DaggerSessionListFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            etCode = it.getString("ET_CODE_KEY")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribe()
    }

    private fun initViews() {
        setupToolbar()
        initRecycler()

        userAdapter.setItems(listOf("Артем", "Руслан", "Константин", "Виктория"))
        binding.toolbar.addStatusBarSpacer()
    }

    private fun subscribe() {
        setButtonClickListeners()
    }

    private fun setupToolbar() {
        binding.toolbar.setTitleText(getString(R.string.session_list_name) + " " + etCode)
    }

    private fun initRecycler() {
        val flexboxLayoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        val spaceBetweenItems = SPACING_BETWEEN_RV_ITEMS_8_DP.dpToPx()

        binding.rvUser.apply {
            adapter = userAdapter
            layoutManager = flexboxLayoutManager
            addItemDecoration(CustomItemDecorator(spaceBetweenItems))
        }
    }

    private fun setButtonClickListeners() {
        binding.btnExit.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.leave_session_title))
                .setMessage(getString(R.string.leave_session_dialog_message))
                .setPositiveButton(getString(R.string.leave_session_dialog_positive)) { dialog, _ ->
                    viewModel.navigate(R.id.action_sessionListFragment_to_mainFragment)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.leave_session_dialog_negative)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    companion object {
        private const val SPACING_BETWEEN_RV_ITEMS_8_DP = 8
    }
}
