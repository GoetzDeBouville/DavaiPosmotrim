package com.davay.android.feature.createsession.presentation.genre

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentGenreBinding
import com.davay.android.di.AppComponentHolder
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
import com.davay.android.feature.createsession.presentation.genre.adapter.GenreAdapter
import com.davay.android.utils.presentation.UiErrorHandler
import com.davay.android.utils.presentation.UiErrorHandlerImpl
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.launch

class GenreFragment : BaseFragment<FragmentGenreBinding, GenreViewModel>(
    FragmentGenreBinding::inflate
) {
    override val viewModel: GenreViewModel by injectViewModel<GenreViewModel>()
    private var genreAdapter = GenreAdapter { genre ->
        viewModel.genreClicked(genre)
    }

    private val errorHandler: UiErrorHandler = UiErrorHandlerImpl()

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        subscribe()
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
        binding.rvGenre.adapter = genreAdapter
        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        binding.rvGenre.layoutManager = layoutManager
    }

    private fun renderState(state: GenreState) {
        when (state) {
            is GenreState.Loading -> showProgressBar()
            is GenreState.Content -> handleContent(state)
            is GenreState.Error -> handleError(state)
        }
    }

    private fun handleError(state: GenreState.Error) {
        showErrorMessage()
        errorHandler.handleError(
            state.errorType,
            binding.errorMessage
        ) {
            viewModel.getGenreList()
        }
    }

    private fun showErrorMessage() = with(binding) {
        errorMessage.isVisible = true
        progressBar.isVisible = false
        rvGenre.isVisible = false
    }

    private fun handleContent(state: GenreState.Content) {
        genreAdapter.updateItemList(state.genreList)
        showContent()
    }

    private fun showContent() = with(binding) {
        errorMessage.isVisible = false
        progressBar.isVisible = false
        rvGenre.isVisible = true
    }

    private fun showProgressBar() = with(binding) {
        errorMessage.isVisible = false
        progressBar.isVisible = true
        rvGenre.isVisible = false
    }

    fun buttonContinueClicked(): Boolean {
        viewModel.buttonContinueClicked()
        return viewModel.hasSelectedGenres()
    }

    fun resetSelections() {
        viewModel.resetSelections()
        genreAdapter.clearSelections()
    }

    companion object {
        fun newInstance() = GenreFragment()
    }
}
