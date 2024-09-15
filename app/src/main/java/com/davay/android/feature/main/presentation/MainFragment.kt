package com.davay.android.feature.main.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.davai.uikit.MainScreenButtonView
import com.davai.uikit.dialog.MainDialogFragment
import com.davay.android.R
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentMainBinding
import com.davay.android.di.AppComponentHolder
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.changename.presentation.ChangeNameBottomSheetFragment
import com.davay.android.feature.main.di.DaggerMainFragmentComponent

class MainFragment :
    BaseFragment<FragmentMainBinding, MainViewModel>(FragmentMainBinding::inflate) {

    override val viewModel: MainViewModel by injectViewModel<MainViewModel>()

    override fun diComponent(): ScreenComponent = DaggerMainFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(USER_NAME_KEY, viewModel.getUserName())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateMarginLogo()
        parentFragmentManager.setFragmentResultListener(
            ChangeNameBottomSheetFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { requestKey, bundle ->
            if (requestKey == ChangeNameBottomSheetFragment.REQUEST_KEY) {
                val changedName = bundle.getString(ChangeNameBottomSheetFragment.BUNDLE_KEY_NAME)
                updateUserName(changedName)
            }
        }

        addBackPressedCallback()
    }

    override fun initViews() {
        super.initViews()
        updateMarginLogo()
        with(binding) {
            msbCreateSession.setState(MainScreenButtonView.CREATE)
            msbFavorite.setState(MainScreenButtonView.FAVORITE)
            msbJoinSession.setState(MainScreenButtonView.JOIN)
            tvUserName.text = viewModel.getUserName()
        }
    }

    override fun subscribe() {
        with(binding) {
            msbFavorite.setOnClickListener {
                viewModel.navigate(R.id.action_mainFragment_to_matchedSessionListFragment)
            }
            msbJoinSession.setOnClickListener {
                joinSession()
            }
            msbCreateSession.setOnClickListener {
                createSession()
            }
            binding.ivEditUserName.setOnClickListener {
                val currentName = binding.tvUserName.text.toString()
                changeName(currentName)
            }
        }
    }

    private fun updateMarginLogo() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.ivLogo) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun joinSession() {
        viewModel.navigate(R.id.action_mainFragment_to_sessionConnectionFragment)
    }

    private fun changeName(oldName: String) {
        val bottomSheetFragment = ChangeNameBottomSheetFragment.newInstance(oldName)
        bottomSheetFragment.show(parentFragmentManager, "tag")
    }

    private fun createSession() {
        viewModel.navigate(R.id.action_mainFragment_to_createSessionFragment)
    }

    private fun updateUserName(newName: String?) {
        if (newName != null) {
            binding.tvUserName.text = newName
        }
    }

    private fun addBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exit()
                }
            }
        )
    }

    private fun exit() {
        MainDialogFragment.newInstance(
            title = getString(R.string.main_exit_title),
            message = getString(R.string.main_exit_message),
            yesAction = {
                requireActivity().finishAndRemoveTask()
            }
        ).show(parentFragmentManager, null)
    }

    companion object {
        private const val USER_NAME_KEY = "user_name_key"
    }
}
