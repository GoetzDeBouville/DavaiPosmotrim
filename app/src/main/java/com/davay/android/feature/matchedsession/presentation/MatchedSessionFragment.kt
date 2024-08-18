package com.davay.android.feature.matchedsession.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.davai.extensions.dpToPx
import com.davay.android.R
import com.davay.android.base.BaseFragment
import com.davay.android.core.presentation.LastItemDecorator
import com.davay.android.databinding.FragmentMatchedSessionBinding
import com.davay.android.di.AppComponentHolder
import com.davay.android.di.ScreenComponent
import com.davay.android.extensions.formatDateWithoutCurrentYear
import com.davay.android.extensions.timeStamp
import com.davay.android.feature.coincidences.presentation.adapter.MoviesGridAdapter
import com.davay.android.feature.matchedsession.di.DaggerMatchedSessionFragmentComponent
import com.davay.android.feature.matchedsession.presentation.adapter.CustomItemDecorator
import com.davay.android.feature.matchedsession.presentation.adapter.UserAdapter
import com.davay.android.feature.moviecard.presentation.MovieCardFragment
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MatchedSessionFragment :
    BaseFragment<FragmentMatchedSessionBinding, MatchedSessionViewModel>(
        FragmentMatchedSessionBinding::inflate
    ) {

    override val viewModel: MatchedSessionViewModel by injectViewModel<MatchedSessionViewModel>()
    private val moviesGridAdapter = MoviesGridAdapter { movieDetails ->
        val movie = Json.encodeToString(movieDetails)
        val bundle = Bundle().apply {
            putString(MovieCardFragment.MOVIE_DETAILS_KEY, movie)
        }
        viewModel.navigate(R.id.action_matchedSessionFragment_to_movieCardFragment, bundle)
    }
    private val userAdapter = UserAdapter()
    private var sessionId = ""

    override fun diComponent(): ScreenComponent = DaggerMatchedSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            sessionId = it.getString(SESSION_ID, "")
        }
        initUsersRecycler()
        setupMoviesGrid()
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

    private fun setupMoviesGrid() {
        binding.coincidencesList.apply {
            ViewCompat.getRootWindowInsets(binding.root)
                ?.getInsets(WindowInsetsCompat.Type.navigationBars())
                ?.bottom
                ?.let {
                    addItemDecoration(LastItemDecorator(it))
                }
            adapter = moviesGridAdapter
        }
    }

    override fun subscribe() {
        viewModel.getSessionData(sessionId)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: MatchedSessionState) {
        when (state) {
            is MatchedSessionState.Empty -> updateVisibility(emptyMessageIsVisible = true)
            is MatchedSessionState.Loading -> updateVisibility(progressBarIsVisible = true)
            is MatchedSessionState.Data -> {
                val date = state.data.session.date
                val subTitle =
                    resources.getString(R.string.matched_session_session, state.data.session.id)
                setupToolbar(subTitle, date)
                userAdapter.setItems(state.data.session.users)
                updateVisibility(coincidencesListIsVisible = true)
                moviesGridAdapter.setData(state.data.movies)
            }

            is MatchedSessionState.Error -> {
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

    private fun setupToolbar(subTitle: String, date: timeStamp) {
        binding.toolbar.apply {
            setTitleText(date.formatDateWithoutCurrentYear())
            setSubtitleText(subTitle)
            setStartIconClickListener {
                viewModel.navigateBack()
            }
        }
    }

    companion object {
        private const val SPACING_BETWEEN_RV_ITEMS_8_DP = 8
        const val SESSION_ID = "session_id"
    }
}