package com.davay.android.feature.sessionconnection.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseBottomSheetFragment
import com.davay.android.databinding.FragmentSessionConnectionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.sessionconnection.di.DaggerSessionConnectionFragmentComponent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch

class SessionConnectionFragment :
    BaseBottomSheetFragment<FragmentSessionConnectionBinding, SessionConnectionViewModel>(
        FragmentSessionConnectionBinding::inflate
    ) {

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null


    override val viewModel: SessionConnectionViewModel by injectViewModel<SessionConnectionViewModel>()

    override fun diComponent(): ScreenComponent = DaggerSessionConnectionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSoftKeyboard(binding.etCode)
        lifecycleScope.launch {
            viewModel.state.collect { stateHandle(it) }
        }
        setButtonClickListeners()
        binding.etCode.doAfterTextChanged {
            viewModel.textCheck(it)


        }
        binding.etCode.buttonBackHandler = {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetBehavior!!.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> showSoftKeyboard(binding.etCode)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < BOTTOM_SHEET_HIDE_PERCENT) {
                    hideKeyboard(binding.etCode)
                    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        })
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View) {
        val imm =
            ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun stateHandle(state: SessionConnectionState?) {
        binding.tvErrorHint.text = when (state) {
            SessionConnectionState.INVALID_LENGTH -> resources.getString(R.string.sessionconnection_invalid)
            SessionConnectionState.INVALID_FORMAT -> resources.getString(R.string.sessionconnection_no_session)
            SessionConnectionState.SUCCESS, SessionConnectionState.DEFAULT,
            SessionConnectionState.FIELD_EMPTY, null -> ""
        }
    }

    private fun setButtonClickListeners() {
        binding.btnEnter.setOnClickListener {
            buttonClicked()
        }
        binding.etCode.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                buttonClicked()
                true
            } else {
                false
            }
        }
    }

    private fun buttonClicked() {
        viewModel.buttonClicked(binding.etCode.text)
        if (viewModel.state.value == SessionConnectionState.SUCCESS) {
            viewModel.navigate(R.id.action_sessionConnectionFragment_to_sessionListFragment)
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    companion object {
        private const val BOTTOM_SHEET_HIDE_PERCENT = 60
    }
}