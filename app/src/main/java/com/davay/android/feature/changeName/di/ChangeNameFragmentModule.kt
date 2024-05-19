package com.davay.android.feature.changeName.di

import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.changeName.presentation.ChangeNameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ChangeNameFragmentModule {

    @IntoMap
    @ViewModelKey(ChangeNameViewModel::class)
    @Binds
    fun bindVM(impl: ChangeNameViewModel): ViewModel
}
