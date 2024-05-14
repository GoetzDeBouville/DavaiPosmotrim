package com.davay.android.feature.registration.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentRegistrationBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.registration.di.DaggerRegistrationFragmentComponent
import kotlinx.coroutines.launch

class RegistrationFragment() :
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
        binding.btnEnter.setOnClickListener {
            viewModel.buttonClicked(binding.etName.text)
        }
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun stateHandle(state: RegistrationState?) {
        val text: String
        when (state) {
            RegistrationState.FIELD_EMPTY -> {
                text = resources.getString(R.string.enter_name)
            }

            RegistrationState.MINIMUM_LETTERS -> {
                text = resources.getString(R.string.two_letters_minimum)
            }

            RegistrationState.NUMBERS -> {
                text = resources.getString(R.string.just_letters)
            }

            RegistrationState.DEFAULT, null -> {
                text = ""
            }

            RegistrationState.SUCCESS -> {
                text = ""
                // viewModel.navigate()
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvErrorHint.text = text
    }
}
