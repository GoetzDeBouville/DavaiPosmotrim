package com.davay.android.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.davay.android.di.ScreenComponent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val inflate: Inflate<VB>,
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    abstract val viewModel: VM

    open val viewModelFactory: ViewModelProvider.Factory by lazy {
        with(diComponent()) {
            viewModelFactory
        }
    }

    protected abstract fun diComponent(): ScreenComponent

    inline fun <reified VM : BaseViewModel> injectViewModel() = viewModels<VM>(
        factoryProducer = { viewModelFactory }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(
                navCommand.directions,
                navCommand.bundle
            )

            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

    // для диалогов с клавиатурой
    protected fun makeDialogWithKeyboard(savedInstanceState: Bundle?): Dialog {
        val thisDdailog = super.onCreateDialog(savedInstanceState)
        thisDdailog.window?.also {
            it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            WindowCompat.setDecorFitsSystemWindows(it, false)
        }
        return thisDdailog
    }

    // для диалогов с клавиатурой
    protected fun moveBottomView(movingView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(requireActivity().window.decorView) { _, windowInsets ->
            @Suppress("TooGenericExceptionCaught")
            try {
                var insetsIme = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
                val insetsNav = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
                movingView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = if (windowInsets.isVisible(WindowInsetsCompat.Type.ime())) {
                        insetsIme.bottom - insetsNav.bottom
                    } else {
                        0
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
            binding.root.rootView,
            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
                private var startBottom = 0f
                private var endBottom = 0f

                override fun onPrepare(
                    animation: WindowInsetsAnimationCompat
                ) {
                    startBottom = binding.root.rootView.bottom.toFloat()
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
                    movingView.translationY =
                        (startBottom - endBottom) * (1 - imeAnimation.interpolatedFraction)
                    return insets
                }
            }
        )
    }
}