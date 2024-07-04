package com.davay.android.feature.matchedsession.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.davai.extensions.dpToPx
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentMatchedSessionBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.coincidences.presentation.adapter.MoviesGridAdapter
import com.davay.android.feature.matchedsession.di.DaggerMatchedSessionFragmentComponent
import com.davay.android.feature.waitsession.presentation.adapter.CustomItemDecorator
import com.davay.android.feature.waitsession.presentation.adapter.UserAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

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

        binding.toolbar.addStatusBarSpacer()
        binding.toolbar.setTitleText("23 сентября")
        binding.toolbar.setSubtitleText("VMst456")
        initUsersRecycler()
        setupMoviesGrid()
        userAdapter.setItems(
            listOf("Артем", "Руслан", "Константин", "Виктория")
        )

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

    companion object {
        private const val SPACING_BETWEEN_RV_ITEMS_8_DP = 8
    }
}