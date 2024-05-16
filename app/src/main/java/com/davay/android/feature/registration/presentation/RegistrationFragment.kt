package com.davay.android.feature.registration.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentRegistrationBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.registration.di.DaggerRegistrationFragmentComponent
import kotlinx.coroutines.launch

class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>(FragmentRegistrationBinding::inflate) {

    override val viewModel: RegistrationViewModel by injectViewModel<RegistrationViewModel>()

    override fun diComponent(): ScreenComponent = DaggerRegistrationFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSoftKeyboard(binding.etName)
        lifecycleScope.launch {
            viewModel.state.collect { stateHandle(it) }
        }
        setButtonClickListeners()
        binding.etName.doAfterTextChanged {
            viewModel.textCheck(it)
            if (it?.length!! >= TYPE_SMALL_BORDER) {
                binding.etName.setTextAppearance(com.davai.uikit.R.style.MediumTextEditText)
            } else {
                binding.etName.setTextAppearance(com.davai.uikit.R.style.BigTextEditText)
            }
        }
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun stateHandle(state: RegistrationState?) {
        binding.tvErrorHint.text = when (state) {
            RegistrationState.FIELD_EMPTY -> resources.getString(R.string.enter_name)
            RegistrationState.MINIMUM_LETTERS -> resources.getString(R.string.two_letters_minimum)
            RegistrationState.NUMBERS -> resources.getString(R.string.just_letters)
            RegistrationState.SUCCESS, RegistrationState.DEFAULT, null -> ""
            RegistrationState.MAXIMUM_LETTERS -> resources.getString(R.string.not_more_letters)
        }
    }

    private fun setButtonClickListeners() {
        binding.btnEnter.setOnClickListener {
            buttonClicked()
        }
        binding.etName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                buttonClicked()
                true
            } else {
                false
            }
        }
    }

    private fun buttonClicked() {
        viewModel.buttonClicked(binding.etName.text)
        if (viewModel.state.value == RegistrationState.SUCCESS) {
            // что-то делаем
            Toast.makeText(requireContext(), "navigate", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TYPE_SMALL_BORDER = 12
    }
}
