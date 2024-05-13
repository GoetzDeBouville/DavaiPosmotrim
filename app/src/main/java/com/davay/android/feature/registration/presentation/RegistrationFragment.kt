package com.davay.android.feature.registration.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentRegistrationBinding

class RegistrationFragment() :
    BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>(FragmentRegistrationBinding::inflate) {

    override val viewModel: RegistrationViewModel by injectViewModel<RegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSoftKeyboard(binding.etName)
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
