package com.davay.android.feature.coincidences.presentation

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
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

class CoincidencesFragment : BaseFragment<FragmentCoincidencesBinding, CoincidencesViewModel>(FragmentCoincidencesBinding::inflate) {

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
                    when {
                        it.isLoading ->
                    }
                }
            }
        }
    }
}