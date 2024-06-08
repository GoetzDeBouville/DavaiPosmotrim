package com.davay.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.davay.android.di.ScreenComponent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

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
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions, navCommand.bundle)
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

}