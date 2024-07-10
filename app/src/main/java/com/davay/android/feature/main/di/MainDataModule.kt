package com.davay.android.feature.main.di

import android.content.SharedPreferences
import com.davay.android.base.repositories.GetSPUserRepository
import com.davay.android.base.usecases.GetSharedPreferences
import dagger.Module
import dagger.Provides

@Module
class MainDataModule {
    @Provides
    fun provideGetSharedPreferences(
        storage: SharedPreferences
    ): GetSharedPreferences<String> = GetSPUserRepository(storage)
}