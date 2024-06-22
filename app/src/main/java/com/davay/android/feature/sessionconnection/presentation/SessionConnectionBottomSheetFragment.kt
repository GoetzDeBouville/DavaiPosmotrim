package com.davay.android.feature.sessionconnection.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseBottomSheetFragment
import com.davay.android.databinding.FragmentSessionConnectionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.sessionconnection.di.DaggerSessionConnectionFragmentComponent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch

class SessionConnectionBottomSheetFragment :
    BaseBottomSheetFragment<FragmentSessionConnectionBinding, SessionConnectionViewModel>(
        FragmentSessionConnectionBinding::inflate
    ) {

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null

    override val viewModel: SessionConnectionViewModel by injectViewModel<SessionConnectionViewModel>()

    override fun diComponent(): ScreenComponent = DaggerSessionConnectionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val thisDdailog = super.onCreateDialog(savedInstanceState)
        thisDdailog.window?.also {
            it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            WindowCompat.setDecorFitsSystemWindows(it, false)
        }
        return thisDdailog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        subscribe()
        animateKeyboard()
    }

    private fun animateKeyboard() {
        ViewCompat.setOnApplyWindowInsetsListener(requireActivity().window.decorView) { _, windowInsets ->
            try {
                var insetsIme = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
                val insetsNav = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
                if (windowInsets.isVisible(WindowInsetsCompat.Type.ime())) {
                    binding.btnEnter.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        bottomMargin = insetsIme.bottom - insetsNav.bottom
                    }
                } else {
                    binding.btnEnter.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        bottomMargin = 0
                    }
                }
            } catch (e: NullPointerException) {
                // ничего не делаем
                e.printStackTrace()
            }
            windowInsets
        }

        // api 30+
        ViewCompat.setWindowInsetsAnimationCallback(
            requireView().rootView,
            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
                var startBottom = 0f
                var endBottom = 0f

                override fun onPrepare(
                    animation: WindowInsetsAnimationCompat
                ) {
                    startBottom = requireView().rootView.bottom.toFloat()
                }

                override fun onStart(
                    animation: WindowInsetsAnimationCompat,
                    bounds: WindowInsetsAnimationCompat.BoundsCompat
                ): WindowInsetsAnimationCompat.BoundsCompat {
                    endBottom = binding.root.bottom.toFloat()
                    return bounds
                }

                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
                ): WindowInsetsCompat {
                    val imeAnimation = runningAnimations.find {
                        it.typeMask and WindowInsetsCompat.Type.ime() != 0
                    } ?: return insets
                    binding.btnEnter.translationY =
                        (startBottom - endBottom) * (1 - imeAnimation.interpolatedFraction)
                    return insets
                }
            }
        )
    }

    private fun initViews(view: View) {
        initBottomSheet(view)
        setupEditText()
        showSoftKeyboard(binding.etCode)
    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.state.collect { stateHandle(it) }
        }
        setButtonClickListeners()
    }

    private fun initBottomSheet(view: View) {
        val parentView = view.parent as? View ?: return
        bottomSheetBehavior = BottomSheetBehavior.from(parentView)

        parentView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val displayMetrics = resources.displayMetrics
                val screenHeight = displayMetrics.heightPixels
                val desiredHeight = (screenHeight * BOTTOM_SHEET_HEIGHT).toInt()

                parentView.layoutParams.height = desiredHeight
                parentView.requestLayout()

                bottomSheetBehavior?.peekHeight = desiredHeight
                parentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    showSoftKeyboard(binding.etCode)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < BOTTOM_SHEET_HIDE_PERCENT_60) {
                    hideKeyboard(binding.etCode)
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        })
    }

    private fun setupEditText() {
        binding.etCode.doAfterTextChanged {
            viewModel.textCheck(it)
        }
        binding.etCode.buttonBackHandler = {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
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

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
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
    }

    private fun buttonClicked() {
        val etCodeValue = binding.etCode.text.toString()
        val bundle = Bundle().apply {
            putString("ET_CODE_KEY", etCodeValue)
        }
        viewModel.buttonClicked(binding.etCode.text)
        if (viewModel.state.value == SessionConnectionState.SUCCESS) {
            findNavController().navigate(
                R.id.action_sessionConnectionFragment_to_sessionListFragment,
                bundle
            )
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    companion object {
        private const val BOTTOM_SHEET_HIDE_PERCENT_60 = 60
        private const val BOTTOM_SHEET_HEIGHT = 0.9
    }
}
