package com.davay.android.feature.createsession.presentation

import android.os.Bundle
import android.view.View
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentGenreBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class GenreFragment : BaseFragment<FragmentGenreBinding, GenreViewModel>(
    FragmentGenreBinding::inflate
) {
    override val viewModel: GenreViewModel by injectViewModel<GenreViewModel>()
    private var genreAdapter: GenreAdapter? = null

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        genreAdapter = GenreAdapter { genre ->
            viewModel.genreClicked(genre)
        }
        binding.rvGenre.adapter = genreAdapter
        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        binding.rvGenre.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        genreAdapter = null
    }

    companion object {
        fun newInstance() = GenreFragment()
    }
}
