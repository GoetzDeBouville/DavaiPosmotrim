package com.davay.android.feature.createsession.presentation.compilations

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCompilationsBinding
import com.davay.android.di.AppComponentHolder
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
import com.davay.android.feature.createsession.presentation.compilations.adapter.CompilationsAdapter
import com.davay.android.utils.presentation.UiErrorHandler
import com.davay.android.utils.presentation.UiErrorHandlerImpl
import kotlinx.coroutines.launch

class CompilationsFragment : BaseFragment<FragmentCompilationsBinding, CompilationsViewModel>(
    FragmentCompilationsBinding::inflate
) {
    override val viewModel: CompilationsViewModel by injectViewModel<CompilationsViewModel>()
    private var compilationAdapter = CompilationsAdapter {
        viewModel.compilationClicked(it)
    }
    private val errorHandler: UiErrorHandler = UiErrorHandlerImpl()

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun subscribe() {
        super.subscribe()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderState(state)
            }
        }
    }

    private fun initRecycler() {
        binding.rvCompilations.adapter = compilationAdapter
        binding.rvCompilations.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun renderState(state: CompilationsState) {
        when (state) {
            is CompilationsState.Loading -> showProgressBar()
            is CompilationsState.Content -> handleContent(state)
            is CompilationsState.Error -> handleError(state)
        }
    }

    private fun handleError(state: CompilationsState.Error) {
        showErrorMessage()
        errorHandler.handleError(
            state.errorType,
            binding.errorMessage
        ) {
            viewModel.getCollectionList()
        }
    }

    private fun showErrorMessage() = with(binding) {
        errorMessage.isVisible = true
        progressBar.isVisible = false
        rvCompilations.isVisible = false
    }

    private fun handleContent(state: CompilationsState.Content) {
        compilationAdapter.addItemList(state.compilationList)
        showContent()
    }

    private fun showContent() = with(binding) {
        errorMessage.isVisible = false
        progressBar.isVisible = false
        rvCompilations.isVisible = true
    }

    private fun showProgressBar() = with(binding) {
        errorMessage.isVisible = true
        progressBar.isVisible = false
        rvCompilations.isVisible = false
    }

    companion object {
        fun newInstance() = CompilationsFragment()
    }
}
