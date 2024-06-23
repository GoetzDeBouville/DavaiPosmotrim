package com.davay.android.feature.coincidences.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCoincidencesBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.coincidences.bottomsheetdialog.RouletteBottomSheetDialogFragment
import com.davay.android.feature.coincidences.di.DaggerCoincidencesFragmentComponent
import com.davay.android.feature.coincidences.presentation.adapter.MoviesGridAdapter
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
        setupToolbar()
        setupMoviesGrid()
        subscribe()
        viewModel.getCoincidences()
        showBottomSheetDialogFragment()
    }

    private fun setupMoviesGrid() = with(binding.coincidencesList) {
        adapter = moviesGridAdapter
    }

    private fun setupToolbar() {
        binding.toolbarView.apply {
            addStatusBarSpacer()
            setEndIconClickListener {
                Toast.makeText(requireContext(), "Navigate to random.", Toast.LENGTH_SHORT).show()
            }
            setStartIconClickListener {
                viewModel.navigateBack()
            }
        }
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
                Toast.makeText(requireContext(), "Error occurred!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateVisibility(
        progressBarIsVisible: Boolean = false,
        coincidencesListIsVisible: Boolean = false,
        emptyMessageIsVisible: Boolean = false
    ) = with(binding) {
        progressBar.isVisible = progressBarIsVisible
        coincidencesList.isVisible = coincidencesListIsVisible
        emptyPlaceholder.root.isVisible = emptyMessageIsVisible
    }

    private fun showBottomSheetDialogFragment() {
        val bottomSheetFragment = RouletteBottomSheetDialogFragment {
            hidePointersAndShadow()
        }
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        showPointersAndShadow()
    }

    private fun showPointersAndShadow() = with(binding) {
        listOf(ivFigureArrow, viewDialogShadow).forEach {
            it.visibility = View.VISIBLE
            addStatusBarSpacerAndShowPointer(requireView())
        }
    }

    private fun hidePointersAndShadow() = with(binding) {
        listOf(ivDices, ivWhitePointer, ivFigureArrow, viewDialogShadow).forEach {
            it.visibility = View.GONE
        }
    }

    private fun addStatusBarSpacerAndShowPointer(view: View) {
        var statusBarHeight: Int
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            Log.v("MyLog", "statusBarHeight: $statusBarHeight")
            applySpaceHeightAndShowPointer(statusBarHeight)
            insets
        }
        view.requestApplyInsets()
    }

    private fun applySpaceHeightAndShowPointer(statusBarHeight: Int) = with(binding) {
        topSpace.let {
            val layoutParams = it.layoutParams
            layoutParams.height = statusBarHeight
            it.layoutParams = layoutParams
        }

        ivWhitePointer.post {
            val layoutParams = ivWhitePointer.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = statusBarHeight
            ivWhitePointer.apply {
                visibility = View.VISIBLE
                this.layoutParams = layoutParams
            }
            ivDices.visibility = View.VISIBLE
        }
    }
}