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
        observeState()
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

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        is UiState.Empty -> hideExclude(emptyMessage = true)
                        is UiState.Loading -> hideExclude(progressBar = true)
                        is UiState.Data -> {
                            hideExclude(coincidencesList = true)
                            moviesGridAdapter.setData(it.data)
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
            }
        }
    }

    private fun hideExclude(
        progressBar: Boolean = false,
//        error: Boolean = false,
        coincidencesList: Boolean = false,
        emptyMessage: Boolean = false
    ) {
        binding.progressBar.isVisible = progressBar
//        binding.error.isVisible = error
        binding.coincidencesList.isVisible = coincidencesList
        binding.emptyPlaceholder.root.isVisible = emptyMessage
    }
}