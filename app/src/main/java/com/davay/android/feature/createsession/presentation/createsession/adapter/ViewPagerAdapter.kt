package com.davay.android.feature.createsession.presentation.createsession.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.davay.android.feature.createsession.presentation.compilations.CompilationsFragment
import com.davay.android.feature.createsession.presentation.genre.GenreFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CompilationsFragment.newInstance()
            else -> GenreFragment.newInstance()
        }
    }
}
