package com.davay.android.feature.main.presentation

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.davai.uikit.MainScreenButtonView
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentMainBinding
import com.davay.android.di.ContextModule
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.changename.presentation.ChangeNameBottomSheetFragment
import com.davay.android.feature.main.di.DaggerMainFragmentComponent

class MainFragment :
    BaseFragment<FragmentMainBinding, MainViewModel>(FragmentMainBinding::inflate) {

    override val viewModel: MainViewModel by injectViewModel<MainViewModel>()
    private var changedName: String? = null
    private val nameSharedPrefs by lazy {
        context?.getSharedPreferences(USER_NAME_KEY,MODE_PRIVATE)
    }

    override fun diComponent(): ScreenComponent = DaggerMainFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(USER_NAME_KEY, binding.userName.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createSession.setState(MainScreenButtonView.CREATE)
        binding.favorite.setState(MainScreenButtonView.FAVORITE)
        binding.joinSession.setState(MainScreenButtonView.JOIN)

        binding.userName.text = nameSharedPrefs?.getString(USER_NAME_KEY,"Артём")
        binding.createSession.setOnClickListener {
            createSession()
        }
        binding.favorite.setOnClickListener {
            Toast.makeText(requireContext(), "Favorite", Toast.LENGTH_SHORT).show()
        }
        binding.joinSession.setOnClickListener {
            joinSession()
        }
        binding.editUserName.setOnClickListener {
            val currentName = binding.userName.text.toString()
            changeName(currentName)
        }
        updateMarginLogo()
        parentFragmentManager.setFragmentResultListener(
            ChangeNameBottomSheetFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { requestKey, bundle ->
            if (requestKey == ChangeNameBottomSheetFragment.REQUEST_KEY) {
                changedName = bundle.getString(ChangeNameBottomSheetFragment.BUNDLE_KEY_NAME)
                updateUserName(changedName)
            }
        }
    }

    private fun updateMarginLogo() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.logo) { v, windowInsets ->
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
            binding.userName.text = newName
            nameSharedPrefs?.edit()?.putString(USER_NAME_KEY,newName)?.apply()
        }
    }

    companion object {
        private const val USER_NAME_KEY = "user_name_key"
    }
}
