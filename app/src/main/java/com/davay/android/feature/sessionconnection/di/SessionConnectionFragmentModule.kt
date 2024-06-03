package com.davay.android.feature.sessionconnection.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.sessionconnection.presentation.SessionConnectionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SessionConnectionFragmentModule {
    @IntoMap
    @ViewModelKey(SessionConnectionViewModel::class)
    @Binds
    fun bindVM(impl: SessionConnectionViewModel): ViewModel
}