package com.davay.android.feature.sessionlist.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.davai.extensions.dpToPx
import com.davai.uikit.dialog.MainDialogFragment
import com.davai.util.setOnDebouncedClickListener
import com.davay.android.R
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSessionListBinding
import com.davay.android.di.AppComponentHolder
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.sessionlist.di.DaggerSessionListFragmentComponent
import com.davay.android.feature.sessionlist.presentation.adapter.CustomItemDecorator
import com.davay.android.feature.sessionlist.presentation.adapter.UserAdapter
import com.davay.android.utils.presentation.UiErrorHandler
import com.davay.android.utils.presentation.UiErrorHandlerImpl
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.launch

class SessionListFragment : BaseFragment<FragmentSessionListBinding, SessionListViewModel>(
    FragmentSessionListBinding::inflate
) {
    override val viewModel: SessionListViewModel by injectViewModel<SessionListViewModel>()
    private val userAdapter = UserAdapter()
    private var dialog: MainDialogFragment? = null
    private val errorHandler: UiErrorHandler = UiErrorHandlerImpl()
    private val args by navArgs<SessionListFragmentArgs>()

    override fun diComponent(): ScreenComponent = DaggerSessionListFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    dialog?.show(parentFragmentManager, CUSTOM_DIALOG_TAG)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = MainDialogFragment.newInstance(
            title = getString(R.string.leave_session_title),
            message = getString(R.string.leave_session_dialog_message),
            yesAction = {
                viewModel.leaveSessionAndNavigateBack(args.etCode)
            }
        )
    }

    override fun initViews() {
        setupToolbar()
        initRecycler()
    }

    override fun subscribe() {
        setButtonClickListeners()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderState(state)
            }
        }
        viewModel.connectToSessionAuto(args.etCode)
    }

    private fun setupToolbar() {
        val args: SessionListFragmentArgs by navArgs()
        val etCode = args.etCode
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
        binding.btnExit.setOnDebouncedClickListener(coroutineScope = lifecycleScope) {
            dialog?.show(parentFragmentManager, CUSTOM_DIALOG_TAG)
        }
    }

    private fun renderState(state: ConnectToSessionState) {
        when (state) {
            is ConnectToSessionState.Loading -> {
                binding.prBar.visibility = View.VISIBLE
            }

            is ConnectToSessionState.Content -> {
                userAdapter.setItems(state.session.users)
                with(binding) {
                    prBar.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                    rvUser.visibility = View.VISIBLE
                    ivSessionListPlaceholder.visibility = View.VISIBLE
                }
            }

            is ConnectToSessionState.Error -> {
                handleError(state)
            }
        }
    }

    private fun handleError(state: ConnectToSessionState.Error) {
        showErrorMessage()
        errorHandler.handleError(
            state.errorType,
            binding.errorMessage
        ) {
            viewModel.connectToSession(args.etCode)
        }
    }

    private fun showErrorMessage() = with(binding) {
        errorMessage.isVisible = true
        prBar.isVisible = false
        rvUser.isVisible = false
        ivSessionListPlaceholder.isVisible = false
    }

    companion object {
        private const val SPACING_BETWEEN_RV_ITEMS_8_DP = 8
        private const val CUSTOM_DIALOG_TAG = "customDialog"
    }
}
