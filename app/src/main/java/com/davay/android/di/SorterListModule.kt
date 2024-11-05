package com.davay.android.di

import com.davay.android.utils.SorterList
import dagger.Module
import dagger.Provides

@Module
class SorterListModule {
    @Provides
    fun provideSorterList(): SorterList = SorterList()
}