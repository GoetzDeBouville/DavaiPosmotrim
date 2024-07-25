package com.davay.android.feature.createsession.presentation.genre

import android.os.Bundle
import android.view.View
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentGenreBinding
import com.davay.android.di.AppComponentHolder
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
import com.davay.android.feature.createsession.domain.model.GenreSelect
import com.davay.android.feature.createsession.presentation.genre.adapter.GenreAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class GenreFragment : BaseFragment<FragmentGenreBinding, GenreViewModel>(
    FragmentGenreBinding::inflate
) {
    override val viewModel: GenreViewModel by injectViewModel<GenreViewModel>()
    private var genreAdapter = GenreAdapter { genre ->
        viewModel.genreClicked(genre)
    }

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvGenre.adapter = genreAdapter
        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        binding.rvGenre.layoutManager = layoutManager
        // временно для теста
        genreAdapter.addItemList(
            listOf(
                GenreSelect(1, "Ужасы"),
                GenreSelect(1, "Комедия"),
                GenreSelect(1, "Боевик"),
                GenreSelect(1, "Ужасы2"),
                GenreSelect(1, "Ужасы3"),
                GenreSelect(1, "Боевик2"),
                GenreSelect(1, "Ужасы4"),
                GenreSelect(1, "Комедия2")
            )
        )
    }

    companion object {
        fun newInstance() = GenreFragment()
    }
}
