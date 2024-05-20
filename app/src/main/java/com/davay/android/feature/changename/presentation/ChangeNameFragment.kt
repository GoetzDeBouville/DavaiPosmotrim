package com.davay.android.feature.changename.presentation

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
import com.davay.android.databinding.FragmentNameChangeBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.changename.di.DaggerChangeNameFragmentComponent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.coroutines.launch

class ChangeNameFragment : BaseBottomSheetFragment<
    FragmentNameChangeBinding,
    ChangeNameViewModel
    >(FragmentNameChangeBinding::inflate) {

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var name: String? = null

    override val viewModel: ChangeNameViewModel by injectViewModel<ChangeNameViewModel>()

    override fun diComponent(): ScreenComponent = DaggerChangeNameFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            name = it.getString(ARG_NAME)
        }

        name?.let {
            binding.etName.setText(name)
        }

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
        binding.etName.buttonBackHandler = {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetBehavior!!.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> showSoftKeyboard(binding.etName)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < BOTTOM_SHEET_HIDE_PERCENT) {
                    hideKeyboard(binding.etName)
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

    private fun stateHandle(state: ChangeNameState?) {
        binding.tvErrorHint.text = when (state) {
            ChangeNameState.FIELD_EMPTY -> resources.getString(R.string.registration_enter_name)
            ChangeNameState.MINIMUM_LETTERS -> resources.getString(R.string.registration_two_letters_minimum)
            ChangeNameState.NUMBERS -> resources.getString(R.string.registration_just_letters)
            ChangeNameState.SUCCESS, ChangeNameState.DEFAULT, null -> ""
            ChangeNameState.MAXIMUM_LETTERS -> resources.getString(R.string.registration_not_more_letters)
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
        if (viewModel.state.value == ChangeNameState.SUCCESS) {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    companion object {
        private const val TYPE_SMALL_BORDER = 12
        private const val BOTTOM_SHEET_HIDE_PERCENT = 60

        private const val ARG_NAME = "name"

        fun newInstance(name: String) = ChangeNameFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, name)
            }
        }
    }
}