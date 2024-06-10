package com.davay.android.feature.coincidences.presentation

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCoincidencesBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.coincidences.di.DaggerCoincidencesFragmentComponent
import com.davay.android.feature.coincidences.presentation.adapter.MoviesGridAdapter
import com.davay.android.feature.coincidences.presentation.adapter.MoviesGridDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CoincidencesFragment : BaseFragment<FragmentCoincidencesBinding, CoincidencesViewModel>(
    FragmentCoincidencesBinding::inflate
) {

    override val viewModel: CoincidencesViewModel by injectViewModel<CoincidencesViewModel>()

    private val moviesGridAdapter = MoviesGridAdapter { movieId ->
        Toast.makeText(requireContext(), "Clicked!", Toast.LENGTH_SHORT).show()
    }

    override fun diComponent(): ScreenComponent = DaggerCoincidencesFragmentComponent
        .builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarView.addStatusBarSpacer()
        setupMoviesGrid()
        subscribe()
        getData()
    }

    private fun setupMoviesGrid() = with(binding.coincidencesList) {
        addItemDecoration(MoviesGridDecoration())
        adapter = moviesGridAdapter
    }

    private fun getData() {
        val connectivityManager = ContextCompat.getSystemService(requireContext(), ConnectivityManager::class.java)
        viewModel.onGetData(connectivityManager)
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: UiState) {
        when (state) {
            is UiState.Empty -> updateVisibility(emptyMessageIsVisible = true)
            is UiState.Loading -> updateVisibility(progressBarIsVisible = true)
            is UiState.Data -> {
                updateVisibility(coincidencesListIsVisible = true)
                moviesGridAdapter.setData(state.data)
            }
            is UiState.Error -> {
//                            hideExclude(error = true)
//                            when (it.errorType) {
//                                ErrorType.NO_INTERNET -> { }
//                                ErrorType.SERVER_ERROR -> { }
//                            }
            }
        }
    }

    private fun updateVisibility(
        progressBarIsVisible: Boolean = false,
//        errorIsVisible: Boolean = false,
        coincidencesListIsVisible: Boolean = false,
        emptyMessageIsVisible: Boolean = false
    ) = with(binding) {
        progressBar.isVisible = progressBarIsVisible
//        error.isVisible = error
        coincidencesList.isVisible = coincidencesListIsVisible
        emptyPlaceholder.root.isVisible = emptyMessageIsVisible
    }
}