package com.davay.android.feature.roulette.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import com.davai.uikit.TagView
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentRouletteBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.roulette.di.DaggerRouletteFragmentComponent
import com.davay.android.feature.roulette.presentation.carouselrecycler.BoundsOffsetDecoration
import com.davay.android.feature.roulette.presentation.carouselrecycler.CarouselAdapter
import com.davay.android.feature.roulette.presentation.carouselrecycler.CarouselLayoutManager
import com.davay.android.feature.roulette.presentation.carouselrecycler.LinearHorizontalSpacingDecoration
import com.davay.android.feature.roulette.presentation.model.FilmRouletteModel
import com.davay.android.feature.roulette.presentation.model.UserRouletteModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class RouletteFragment :
    BaseFragment<FragmentRouletteBinding, RouletteViewModel>(FragmentRouletteBinding::inflate) {

    override val viewModel: RouletteViewModel by injectViewModel<RouletteViewModel>()
    private val carouselAdapter: CarouselAdapter = CarouselAdapter()
    private val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.bottomSheetRoulette) }

    override fun diComponent(): ScreenComponent =
        DaggerRouletteFragmentComponent.builder().appComponent(AppComponentHolder.getComponent())
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheet()
        initRecyclerRoulette()
    }

    private fun initRecyclerRoulette() {
        carouselAdapter.addFilms(
            listOf(
                FilmRouletteModel(
                    id = 1,
                    title = "Название 1",
                    mark = "5.0",
                    originalTitle = "gfdgd",
                    yearCountryRuntime = "2008, USA",
                    posterUrl = "https://atthemovies.uk/cdn/shop/files/" +
                            "front_07977eff-c3c7-4aa6-a57d-5d3e34727031.png?v=1717112425"
                ),
                FilmRouletteModel(
                    id = 2,
                    title = "Название 2",
                    mark = "7.0",
                    originalTitle = "gfdgd",
                    yearCountryRuntime = "2008, USA",
                    posterUrl = "https://atthemovies.uk/cdn/shop/files/" +
                            "back_ff1004f8-11e7-4e2b-81de-a9cd2a3de2c0.png?v=1717112172"
                ),
                FilmRouletteModel(
                    id = 3,
                    title = "Название 3",
                    mark = "9.4",
                    originalTitle = "gfdgd",
                    yearCountryRuntime = "1991, UK",
                    posterUrl = "https://atthemovies.uk/cdn/shop/products/" +
                            "Gladiator2000us27x40in195u.jpg?v=1621385091"
                ),
                FilmRouletteModel(
                    id = 4,
                    title = "Название 4",
                    mark = "5.0",
                    originalTitle = "gfdgd",
                    yearCountryRuntime = "2018, USA",
                    posterUrl = "https://atthemovies.uk/cdn/shop/files/" +
                            "front_efd356d9-ab5b-4dc3-a37f-452027a2b68d.png?v=1717110841"
                ),
                FilmRouletteModel(
                    id = 5,
                    title = "Название 5",
                    mark = "3.0",
                    originalTitle = "hjjgjkhk hjjh jhjhjk hjkjk",
                    yearCountryRuntime = "2010, Canada",
                    posterUrl = "https://atthemovies.uk/cdn/shop/products/" +
                            "Template_cb39750d-7a4b-44ec-950d-172f4c2fe96c.jpg?v=1666078856"
                ),
            )
        )
        with(binding.recyclerViewRoulette) {
            layoutManager = CarouselLayoutManager(requireContext())
            adapter = carouselAdapter
            val spacing = resources.getDimensionPixelSize(com.davai.uikit.R.dimen.margin_16)
            addItemDecoration(LinearHorizontalSpacingDecoration(spacing))
            addItemDecoration(BoundsOffsetDecoration())
            LinearSnapHelper().attachToRecyclerView(this)
        }
        startAutoScrolling()
        // для примера: останавливаем автопрокрутку и запускаем рулетку
        lifecycleScope.launch {
            delay(3000)
            binding.recyclerViewRoulette.stopScroll()
            hideBottomSheet()
            delay(1000)
            val position = Random
                .nextInt(10) + 30 + (binding.recyclerViewRoulette.layoutManager as CarouselLayoutManager)
                .findLastVisibleItemPosition()
            startRouletteScrolling(position)
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

    /*
    private fun stopAutoScrolling() {
        with(binding.recyclerViewRoulette) {
            post {
                val position =
                    (layoutManager as CarouselLayoutManager).findLastVisibleItemPosition()
                smoothScrollToPosition(position)
            }
        }
    }
    */

    private fun startRouletteScrolling(position: Int) {
        with(binding.recyclerViewRoulette) {
            (layoutManager as CarouselLayoutManager).speed = CarouselLayoutManager.SPEED_HIGH
            post {
                smoothScrollToPosition(position)
            }
        }
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.fblParticipants.apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        // откуда-то получаем список участников
        val participantsList =
            listOf(UserRouletteModel(1, "Masha"), UserRouletteModel(2, "Sasha"))
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
}
