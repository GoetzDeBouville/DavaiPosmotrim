package com.davay.android.feature.selectmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import com.davai.extensions.dpToPx
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSelectMovieBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.domain.models.MovieDetails
import com.davay.android.extensions.SwipeDirection
import com.davay.android.feature.match.presentation.MatchBottomSheetFragment
import com.davay.android.feature.selectmovie.di.DaggerSelectMovieFragmentComponent
import com.davay.android.feature.selectmovie.presentation.adapters.MovieCardAdapter
import com.davay.android.feature.selectmovie.presentation.adapters.SwipeCallback
import com.davay.android.feature.selectmovie.presentation.adapters.SwipeableLayoutManager
import com.davay.android.feature.selectmovie.presentation.animation.IncrementAnimation
import com.davay.android.feature.selectmovie.presentation.animation.IncrementAnimationImpl
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SelectMovieFragment :
    BaseFragment<FragmentSelectMovieBinding, SelectMovieViewModel>(FragmentSelectMovieBinding::inflate) {

    override val viewModel: SelectMovieViewModel by injectViewModel<SelectMovieViewModel>()
    private var matchesCounter = 0
    private val cardAdapter = MovieCardAdapter(
        swipeLeft = { autoSwipeLeft() },
        swipeRight = { autoSwipeRight() },
        revert = { revertSwipe() },
        inflateMovieDetails = { movie -> inflateMovieDetails(movie) }
    )
    private val swipeCardLayoutManager = SwipeableLayoutManager()
    private val incrementAnimation: IncrementAnimation = IncrementAnimationImpl()
    private var currentPosition = 0

    override fun diComponent(): ScreenComponent =
        DaggerSelectMovieFragmentComponent.builder().appComponent(AppComponentHolder.getComponent())
            .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSavedPositionAndUpdateStartPosition(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveHistory()
    }

    override fun onDestroyView() {
        binding.rvFilmCard.adapter = null
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentPosition = swipeCardLayoutManager.getCurrentPosition()
        outState.putInt(CURRENT_POSITION_KEY, currentPosition)
    }

    private fun getSavedPositionAndUpdateStartPosition(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            currentPosition = it.getInt(CURRENT_POSITION_KEY, 0)
        }
        swipeCardLayoutManager.updateCurrentPosition(currentPosition)
    }

    override fun initViews() {
        buildRecyclerView()
        setToolbar()
        setBottomSheet()
    }

    override fun subscribe() {
        super.subscribe()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { movies ->
                cardAdapter.setData(movies)
            }
        }
        binding.toolbarviewHeader.setEndIconClickListener {
            if (viewModel.state.value.isNotEmpty()) {
                showBottomSheetFragment(viewModel.state.value.first())
            }
        }
    }

    private fun buildRecyclerView() {
        binding.rvFilmCard.apply {
            layoutManager = swipeCardLayoutManager
            adapter = cardAdapter
            // помимо установки позици на layputmanger дополни тельно скролим до необходимой позиции
            scrollToPosition(currentPosition)
        }

        val itemTouchHelper = ItemTouchHelper(
            SwipeCallback(
                swipeCardLayoutManager,
                onSwipedLeft = {
                    // Add skip method
                },
                onSwipedRight = {
                    // Add like method
                }
            )
        )

        itemTouchHelper.attachToRecyclerView(binding.rvFilmCard)
    }

    private fun setBottomSheet() = with(binding) {
        BottomSheetBehavior.from(clDetailsBottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = BOTTOMSHEET_PEEK_HEIGHT_112_DP.dpToPx()

            clDetailsBottomSheet.post {
                val cardLocation = IntArray(2)
                rvFilmCard.getLocationOnScreen(cardLocation)
                val cardTop = cardLocation[1]

                val screenHeight = activity?.resources?.displayMetrics?.heightPixels ?: 0
                val maxHeight = screenHeight - cardTop + MARGIN_TOP_16_DP.dpToPx()

                clDetailsBottomSheet.layoutParams.height = maxHeight
                clDetailsBottomSheet.requestLayout()
            }
        }
    }

    private fun setToolbar() {
        binding.toolbarviewHeader.apply {
            updateMatchesDisplay(matchesCounter)
        }
    }

    private fun autoSwipeLeft() {
        swipeCardLayoutManager.moveNextWithSwipeAndLayout(SwipeDirection.LEFT)
        cardAdapter.notifyDataSetChanged()
    }

    private fun autoSwipeRight() {
        swipeCardLayoutManager.moveNextWithSwipeAndLayout(SwipeDirection.RIGHT)
        cardAdapter.notifyDataSetChanged()
    }

    private fun revertSwipe() {
        swipeCardLayoutManager.shiftLeftWithRevertAndLayout()
        cardAdapter.notifyDataSetChanged()
    }

    private fun inflateMovieDetails(movie: MovieDetails) = with(binding) {
        tvDetailsDescription.text = movie.description
        setRates(movie)
        inflateCastList(fblDetailsTopCastList, movie.actors.orEmpty())
        inflateCastList(fblDetailsDirectorList, movie.directors.orEmpty())
    }

    private fun setRates(movie: MovieDetails) = with(binding) {
        if (movie.ratingImdb == null) {
            mevDetailsImdbRate.visibility = View.GONE
        } else {
            mevDetailsImdbRate.apply {
                setRateNum(movie.ratingImdb)
                setNumberOfRatesString(movie.numOfMarksImdb ?: 0)
            }
        }

        if (movie.ratingKinopoisk == null) {
            mevDetailsKinopoiskRate.visibility = View.GONE
        } else {
            mevDetailsKinopoiskRate.apply {
                setRateNum(movie.ratingKinopoisk)
                setNumberOfRatesString(movie.numOfMarksKinopoisk ?: 0)
            }
        }
    }

    private fun inflateCastList(fbl: FlexboxLayout, list: List<String>) {
        fbl.removeAllViews()
        val castList = list.take(MAX_CAST_NUMBER_4)
        castList.forEach {
            val castView = LayoutInflater.from(requireContext()).inflate(
                R.layout.item_top_cast,
                fbl,
                false
            ) as TextView
            castView.text = it
            fbl.addView(castView)
        }
    }

    private fun showBottomSheetFragment(movie: MovieDetails) {
        val movieDetails = Gson().toJson(movie)
        val bottomSheetFragment = MatchBottomSheetFragment.newInstance(
            movieDetails,
            action = {
                incrementAnimation.animate(binding.tvMotionedIncrement) {
                    binding.toolbarviewHeader.incrementMatchesDisplay()
                }
            }
        )
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private companion object {
        const val BOTTOMSHEET_PEEK_HEIGHT_112_DP = 112
        const val MARGIN_TOP_16_DP = 16
        const val MAX_CAST_NUMBER_4 = 4
        const val CURRENT_POSITION_KEY = "currentPosition"
    }
}
