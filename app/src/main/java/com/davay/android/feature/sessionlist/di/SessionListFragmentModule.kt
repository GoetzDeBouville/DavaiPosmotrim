package com.davay.android.feature.sessionlist.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.sessionlist.presentation.SessionListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SessionListFragmentModule {
    @IntoMap
    @ViewModelKey(SessionListViewModel::class)
    @Binds
    fun bindVM(impl: SessionListViewModel): ViewModel
}