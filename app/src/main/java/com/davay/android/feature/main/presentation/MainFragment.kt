package com.davay.android.feature.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.davai.uikit.MainScreenButtonView
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentMainBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.main.di.DaggerMainFragmentComponent

class MainFragment :
    BaseFragment<FragmentMainBinding, MainViewModel>(FragmentMainBinding::inflate) {

    override val viewModel: MainViewModel by injectViewModel<MainViewModel>()
    override fun diComponent(): ScreenComponent = DaggerMainFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.createSession.setState(MainScreenButtonView.CREATE)
        binding.favorite.setState(MainScreenButtonView.FAVORITE)
        binding.joinSession.setState(MainScreenButtonView.JOIN)

        // for test
        binding.userName.text = "Артём"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createSession.setOnClickListener {
            Toast.makeText(requireContext(), "Create Session", Toast.LENGTH_SHORT).show()
        }
        binding.favorite.setOnClickListener {
            Toast.makeText(requireContext(), "Favorite", Toast.LENGTH_SHORT).show()
        }
        binding.joinSession.setOnClickListener {
            Toast.makeText(requireContext(), "Join Session", Toast.LENGTH_SHORT).show()
        }
        updateMarginLogo()
    }

    private fun updateMarginLogo() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.logo) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }

        binding.toWaitFragment.setOnClickListener {
            viewModel.navigate(R.id.action_mainFragment_to_waitSessionFragment)
        }
    }
}