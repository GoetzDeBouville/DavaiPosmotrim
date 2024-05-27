package com.davay.android.feature.sessionlist.presentation


import android.os.Bundle
import android.view.View
import com.davay.android.app.AppComponentHolder
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentSessionListBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.sessionlist.di.DaggerSessionListFragmentComponent
import com.davay.android.feature.sessionlist.presentation.adapter.UserAdapter

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

        val currentToolbarText = binding.toolbar.setTitleText("Сеанс" + " " + "VMst457")

//        initRecycler()
//        userAdapter?.itemList?.addAll(
//            listOf("Артем", "Руслан", "Константин", "Виктория")
//        )

    }

}
