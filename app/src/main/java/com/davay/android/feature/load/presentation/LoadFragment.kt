package com.davay.android.feature.load.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentLoadBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.load.di.DaggerLoadFragmentComponent
import com.davay.android.feature.matchedsession.presentation.MatchedSessionFragment
import com.davay.android.feature.onboarding.presentation.OnboardingFragment
import com.davay.android.feature.roulette.presentation.RouletteFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LoadFragment : BaseFragment<FragmentLoadBinding, LoadViewModel>(
    FragmentLoadBinding::inflate
) {

    override val viewModel: LoadViewModel by injectViewModel<LoadViewModel>()
    override fun diComponent(): ScreenComponent =
        DaggerLoadFragmentComponent.builder().appComponent(AppComponentHolder.getComponent())
            .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(
            inflater,
            container,
            savedInstanceState
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToMovieSelection.setOnClickListener {
            viewModel.navigate(R.id.action_loadFragment_to_selectMovieFragment)
        }
        binding.btnToOnboarding.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(OnboardingFragment.ONBOARDING_KEY, OnboardingFragment.ONBOARDING_MAIN_SET)
            }
            viewModel.navigate(R.id.action_loadFragment_to_onboardingFragment, bundle)
        }
        binding.btnToSplash.setOnClickListener {
            viewModel.navigate(R.id.action_loadFragment_to_splashFragment)
        }
        binding.button.setOnClickListener { _ ->
            viewModel.navigate(R.id.action_loadFragment_to_mainFragment)
        }
        binding.button2.setOnClickListener { _ ->
            viewModel.navigate(R.id.action_loadFragment_to_registrationFragment)
        }
        binding.btnToRoulette.setOnClickListener {
            viewModel.navigate(R.id.action_loadFragment_to_rouletteFragment)
        }
        binding.btnToRoulette2.setOnClickListener {
            val bundle = Bundle().apply {
                putBoolean(RouletteFragment.ROULETTE_INITIATOR, true)
            }
            viewModel.navigate(R.id.action_loadFragment_to_rouletteFragment, bundle)
        }
        binding.btnToCoincidences.setOnClickListener {
            viewModel.navigate(R.id.action_loadFragment_to_coincidencesFragment)
        }
        binding.button3.setOnClickListener { _ ->
            viewModel.navigate(R.id.action_loadFragment_to_waitSessionFragment)
        }
        binding.btnToMatchedSession.setOnClickListener {
            viewModel.navigate(R.id.action_loadFragment_to_matchedSessionFragment, getBundleForMatchedSession())
        }
    }

    private fun getBundleForMatchedSession(): Bundle =
//        Данные берем из модели, переход будет со страницы списка сессий, там все это будет
        Bundle().apply {
            putString(MatchedSessionFragment.SUBTITLE, "${getString(R.string.session_list_name)} VMst456")
            putStringArray(MatchedSessionFragment.USERS, arrayOf("Артём", "Виктория", "Иван", "Марина", "Фёдор"))
            val simpleDate =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).parse("2023-09-23") ?: Calendar.getInstance().time
            val formater = SimpleDateFormat("dd MMMM", Locale.getDefault())
            putString(MatchedSessionFragment.DATE, formater.format(simpleDate))
        }
}