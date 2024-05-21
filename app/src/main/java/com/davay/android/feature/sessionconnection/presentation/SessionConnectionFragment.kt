package com.davay.android.feature.sessionconnection.presentation


import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSessionConnectionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.sessionconnection.di.DaggerSessionConnectionFragmentComponent
import kotlinx.coroutines.launch

class SessionConnectionFragment :
    BaseFragment<FragmentSessionConnectionBinding, SessionConnectionViewModel>(
        FragmentSessionConnectionBinding::inflate
    ) {

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
            findNavController().navigateUp()
        }
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun stateHandle(state: SessionConnectionState?) {
        binding.tvErrorHint.text = when (state) {
            SessionConnectionState.INVALID_LENGTH -> resources.getString(R.string.sessionconnection_invalid)
            SessionConnectionState.INVALID_FORMAT -> resources.getString(R.string.sessionconnection_no_session)
            SessionConnectionState.SUCCESS, SessionConnectionState.DEFAULT, SessionConnectionState.FIELD_EMPTY, null -> ""
        }
    }

    private fun setButtonClickListeners() {
        binding.btnEnter.setOnClickListener {
            buttonClicked()
        }
        binding.etCode.setOnEditorActionListener { v, actionId, event ->
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
            //viewModel.navigate(R.id.action_sessionConnectionFragment_to_listOfSessionsFragment)
        }
    }
}