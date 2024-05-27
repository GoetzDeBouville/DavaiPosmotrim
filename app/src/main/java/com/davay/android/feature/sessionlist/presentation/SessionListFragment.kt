package com.davay.android.feature.sessionlist.presentation


import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSessionListBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.sessionlist.di.DaggerSessionListFragmentComponent
import com.davay.android.feature.sessionlist.presentation.adapter.CustomItemDecorator
import com.davay.android.feature.sessionlist.presentation.adapter.UserAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlin.math.roundToInt

class SessionListFragment : BaseFragment<FragmentSessionListBinding, SessionListViewModel>(
    FragmentSessionListBinding::inflate
) {
    override val viewModel: SessionListViewModel by injectViewModel<SessionListViewModel>()
    private var userAdapter: UserAdapter? = null

    override fun diComponent(): ScreenComponent = DaggerSessionListFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setTitleText(getString(R.string.session_list_name) + " " + "VMst457")

        initRecycler()
        userAdapter?.itemList?.addAll(
            listOf("Артем", "Руслан", "Константин", "Виктория")
        )
    }

    private fun initRecycler() {
        userAdapter = UserAdapter()
        binding.rvUser.adapter = userAdapter
        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }

        binding.rvUser.layoutManager = layoutManager
        val spaceBetweenItems = convertPxToDp(requireContext(), SPACING_BETWEEN_RV_ITEMS)
        val itemDecoration = CustomItemDecorator(spaceBetweenItems)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    override fun onDestroyView() {
        binding.rvUser.adapter = null
        super.onDestroyView()
        userAdapter = null
    }

    private fun convertPxToDp(context: Context, px: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return (px * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    companion object {
        private const val SPACING_BETWEEN_RV_ITEMS = 8
    }
}

