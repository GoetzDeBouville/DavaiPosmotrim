package com.davay.android.feature.matchedsession.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.matchedsession.presentation.MatchedSessionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MatchedSessionFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(MatchedSessionViewModel::class)
    fun bindVM(impl: MatchedSessionViewModel): ViewModel
}
