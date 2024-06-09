package com.davay.android.feature.roulette.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.TagView
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentRouletteBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.match.presentation.MatchBottomSheetFragment
import com.davay.android.feature.roulette.di.DaggerRouletteFragmentComponent
import com.davay.android.feature.roulette.presentation.carouselrecycler.CarouselAdapter
import com.davay.android.feature.roulette.presentation.carouselrecycler.CarouselLayoutManager
import com.davay.android.feature.roulette.presentation.carouselrecycler.LinearHorizontalSpacingDecoration
import com.davay.android.feature.roulette.presentation.model.UserRouletteModel
import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RouletteFragment :
    BaseFragment<FragmentRouletteBinding, RouletteViewModel>(FragmentRouletteBinding::inflate) {

    override val viewModel: RouletteViewModel by injectViewModel<RouletteViewModel>()
    private val carouselAdapter: CarouselAdapter = CarouselAdapter()
    private val bottomSheetBehaviorWaiting by lazy { BottomSheetBehavior.from(binding.bottomSheetWaiting) }
    private val bottomSheetBehaviorIntro by lazy { BottomSheetBehavior.from(binding.bottomSheetIntro) }

    override fun diComponent(): ScreenComponent =
        DaggerRouletteFragmentComponent.builder().appComponent(AppComponentHolder.getComponent())
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collect {
                handleState(it)
            }
        }
        handleStartFragment()
    }

    /**
     *  Для инициатора рулетки в bundle должно быть true по ключу ROULETTE_INITIATOR.
     *  Для остальных пусто или false.
     */
    private fun handleStartFragment() {
        val isInitiator: Boolean? = arguments?.getBoolean(ROULETTE_INITIATOR)
        if (isInitiator == true) {
            initBottomSheetIntro()
        } else {
            bottomSheetBehaviorIntro.state = BottomSheetBehavior.STATE_HIDDEN
            startAutoScrolling()
        }
    }

    private fun initBottomSheetIntro() {
        bottomSheetBehaviorIntro.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehaviorIntro.isHideable = false
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnContinue.setOnClickListener {
            bottomSheetBehaviorIntro.isHideable = true
            bottomSheetBehaviorIntro.state = BottomSheetBehavior.STATE_HIDDEN
            viewModel.rouletteStart()
            startAutoScrolling()
        }
    }

    private fun initRecyclerRoulette(films: List<MovieDetailsDemo>) {
        carouselAdapter.addFilms(films)
        with(binding.recyclerViewRoulette) {
            layoutManager = CarouselLayoutManager(requireContext())
            adapter = carouselAdapter
            val spacing = resources.getDimensionPixelSize(com.davai.uikit.R.dimen.zero)
            addItemDecoration(LinearHorizontalSpacingDecoration(spacing))
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun startAutoScrolling() {
        with(binding.recyclerViewRoulette) {
            (layoutManager as CarouselLayoutManager).speed = CarouselLayoutManager.SPEED_LOW
            post {
                smoothScrollToPosition(Int.MAX_VALUE)
            }
        }
    }

    private fun startRouletteScrolling(position: Int) {
        with(binding.recyclerViewRoulette) {
            (layoutManager as CarouselLayoutManager).speed = CarouselLayoutManager.SPEED_HIGH
            post {
                smoothScrollToPosition(position)
            }
            binding.recyclerViewRoulette.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        viewModel.rouletteScrollingStopped()
                    }
                }
            })
        }
    }

    private fun hideBottomSheet() {
        bottomSheetBehaviorWaiting.isHideable = true
        bottomSheetBehaviorWaiting.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initBottomSheetWaiting(participantsList: List<UserRouletteModel>) {
        bottomSheetBehaviorWaiting.state = BottomSheetBehavior.STATE_EXPANDED
        binding.fblParticipants.apply {
            setDividerDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    com.davai.uikit.R.drawable.divider_8,
                    requireActivity().theme
                )
            )
            setShowDivider(FlexboxLayout.SHOW_DIVIDER_MIDDLE)
        }
        participantsList.forEach { user ->
            val participantsView = LayoutInflater.from(requireContext()).inflate(
                R.layout.item_participants,
                binding.fblParticipants,
                false
            ) as TagView
            participantsView.setText(user.name)
            binding.fblParticipants.addView(participantsView)
        }
    }

    private fun handleState(state: RouletteState) {
        when (state) {
            RouletteState.Error -> handleErrorState()
            is RouletteState.Match -> handleMatchState(state)
            is RouletteState.Roulette -> handleRouletteState(state)
            is RouletteState.Waiting -> handleWaitingState(state)
            is RouletteState.Init -> handleInitState(state)
        }
    }

    private fun handleErrorState() {
        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
    }

    private fun handleMatchState(state: RouletteState.Match) {
        val movieDetails = Gson().toJson(state.film)
        val matchBottomSheetFragment = MatchBottomSheetFragment.newInstance(
            movieDetails,
            getString(R.string.roulette_to_film_list)
        )
        matchBottomSheetFragment.show(parentFragmentManager, matchBottomSheetFragment.tag)
    }

    private fun handleRouletteState(state: RouletteState.Roulette) {
        binding.recyclerViewRoulette.stopScroll()
        lifecycleScope.launch {
            delay(DELAY_TIME_MS_1000)
            hideBottomSheet()
            val currentPosition =
                (binding.recyclerViewRoulette.layoutManager as CarouselLayoutManager)
                    .findLastVisibleItemPosition()
            val position =
                currentPosition / state.count * state.count + state.count * ROULETTE_SCROLL_COEFFICIENT + state.index
            startRouletteScrolling(position)
        }
    }

    private fun handleWaitingState(state: RouletteState.Waiting) {
        state.users.forEachIndexed { index, user ->
            if (user.isConnected) {
                // Важен порядок
                (binding.fblParticipants[index] as TagView).changeStyle(TagView.Companion.Style.SECONDARY_GREEN)
            }
        }
    }

    private fun handleInitState(state: RouletteState.Init) {
        initBottomSheetWaiting(state.users)
        initRecyclerRoulette(state.films)
    }

    companion object {
        private const val DELAY_TIME_MS_1000 = 1000L
        private const val ROULETTE_SCROLL_COEFFICIENT = 4
        const val ROULETTE_INITIATOR = "ROULETTE_INITIATOR"
    }
}
