package com.davay.android.feature.matchedsession.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.davai.extensions.dpToPx
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentMatchedSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.coincidences.presentation.UiState
import com.davay.android.feature.matchedsession.di.DaggerMatchedSessionFragmentComponent
import com.davay.android.feature.matchedsession.presentation.adapter.CustomItemDecorator
import com.davay.android.feature.matchedsession.presentation.adapter.MoviesGridAdapter
import com.davay.android.feature.matchedsession.presentation.adapter.UserAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MatchedSessionFragment :
    BaseFragment<FragmentMatchedSessionBinding, MatchedSessionViewModel>(FragmentMatchedSessionBinding::inflate) {

    override val viewModel: MatchedSessionViewModel by injectViewModel<MatchedSessionViewModel>()
    private val moviesGridAdapter = MoviesGridAdapter { movieId ->
        Toast.makeText(requireContext(), "Clicked!", Toast.LENGTH_SHORT).show()
    }
    private val userAdapter = UserAdapter()

    override fun diComponent(): ScreenComponent = DaggerMatchedSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            userAdapter.setItems(it.getStringArray(USERS)?.toList() ?: listOf())
            setupToolbar(
                subTitle = it.getString(SUBTITLE) ?: String(),
                date = it.getString(DATE) ?: String()
            )
        } // Сделал получение данных через бандл, может быть решим по-другому потом
        initUsersRecycler()
        setupMoviesGrid()
        subscribe()
    }

    private fun initUsersRecycler() {
        val flexboxLayoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        val spaceBetweenItems = SPACING_BETWEEN_RV_ITEMS_8_DP.dpToPx()

        binding.rvUser.apply {
            adapter = userAdapter
            layoutManager = flexboxLayoutManager
            addItemDecoration(CustomItemDecorator(spaceBetweenItems))
        }
    }

    private fun setupMoviesGrid() = with(binding.coincidencesList) {
        adapter = moviesGridAdapter
    }
//    Начало -> Взято из совпадений, чисто для демонстрации

    override fun subscribe() {
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
                updateVisibility(errorMessageVisible = true)
            }
        }
    }

    private fun updateVisibility(
        progressBarIsVisible: Boolean = false,
        coincidencesListIsVisible: Boolean = false,
        emptyMessageIsVisible: Boolean = false,
        errorMessageVisible: Boolean = false
    ) = with(binding) {
        progressBar.isVisible = progressBarIsVisible
        coincidencesList.isVisible = coincidencesListIsVisible
        emptyPlaceholder.root.isVisible = emptyMessageIsVisible
        errorMessage.root.isVisible = errorMessageVisible
    }

//        Конец -> Взято из совпадений, чисто для демонстрации

    private fun setupToolbar(subTitle: String, date: String) {
        binding.toolbar.apply {
            setTitleText(date)
            setSubtitleText(subTitle)
            setStartIconClickListener {
                viewModel.navigateBack()
            }
        }
    }

    companion object {
        private const val SPACING_BETWEEN_RV_ITEMS_8_DP = 8
        const val USERS = "users"
        const val DATE = "date"
        const val SUBTITLE = "sessionId"
    }
}