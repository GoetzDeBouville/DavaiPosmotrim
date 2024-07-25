package com.davay.android.feature.createsession.presentation.compilations

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentCompilationsBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.createsession.di.DaggerCreateSessionFragmentComponent
import com.davay.android.feature.createsession.domain.model.Compilation
import com.davay.android.feature.createsession.presentation.compilations.adapter.CompilationsAdapter

class CompilationsFragment : BaseFragment<FragmentCompilationsBinding, CompilationsViewModel>(
    FragmentCompilationsBinding::inflate
) {
    override val viewModel: CompilationsViewModel by injectViewModel<CompilationsViewModel>()
    private var compilationAdapter = CompilationsAdapter {
        viewModel.compilationClicked(it)
    }

    override fun diComponent(): ScreenComponent = DaggerCreateSessionFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent()).build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvCompilations.adapter = compilationAdapter
        binding.rvCompilations.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        // временно для теста
        compilationAdapter.addItemList(
            listOf(
                Compilation(
                    1,
                    "Ужасы",
                    "https://pikuco.ru/upload/test_stable/6f1/6f1bd5d0f587f12f4a1bd9bd107beb56.webp"
                ),
                Compilation(
                    1,
                    "Комедия",
                    "https://ss.sport-express.ru/userfiles/materials/197/1974960/volga.jpg"
                ),
                Compilation(
                    1,
                    "Боевик",
                    "https://s1.afisha.ru/mediastorage/93/da/603bb317d0284ddbb61501aeda93.jpg"
                ),
                Compilation(1, "Ужасы2", ""),
                Compilation(1, "Ужасы3", ""),
                Compilation(1, "Боевик2", ""),
                Compilation(1, "Ужасы4", ""),
                Compilation(1, "Комедия2", "")
            )
        )
    }

    fun buttonContinueClicked(): Boolean {
        viewModel.buttonContinueClicked()
        return viewModel.hasSelectedCompilations()
    }

    companion object {
        fun newInstance() = CompilationsFragment()
    }
}
