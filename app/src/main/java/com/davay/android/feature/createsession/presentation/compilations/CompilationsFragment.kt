package com.davay.android.feature.createsession.presentation.compilations

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCompilationsBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
import com.davay.android.feature.createsession.domain.model.Compilation
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

private val mockList = listOf(
    Compilation(
        "1",
        "Ужасы",
        "https://pikuco.ru/upload/test_stable/6f1/6f1bd5d0f587f12f4a1bd9bd107beb56.webp"
    ),
    Compilation(
        "2",
        "Комедия",
        "https://ss.sport-express.ru/userfiles/materials/197/1974960/volga.jpg"
    ),
    Compilation(
        "3",
        "Боевик",
        "https://s1.afisha.ru/mediastorage/93/da/603bb317d0284ddbb61501aeda93.jpg"
    ),
    Compilation("1", "Ужасы", ""),
    Compilation("2", "Фантастика", ""),
    Compilation("3", "Детектив", ""),
    Compilation("4", "Триллер", ""),
    Compilation("5", "Боевик", "")
)

