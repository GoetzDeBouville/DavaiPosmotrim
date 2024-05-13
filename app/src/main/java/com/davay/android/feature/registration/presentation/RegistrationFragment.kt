package com.davay.android.feature.registration.presentation

import android.os.Bundle
import android.view.View
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentRegistrationBinding

class RegistrationFragment() :
    BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>(FragmentRegistrationBinding::inflate) {

    override val viewModel: RegistrationViewModel by injectViewModel<RegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etName.requestFocus()
    }
}
