package com.davay.android.feature.changename.di

import android.content.SharedPreferences
import com.davay.android.base.repositories.GetSPUserRepository
import com.davay.android.base.repositories.SetSPUserRepository
import com.davay.android.base.usecases.GetSharedPreferences
import com.davay.android.base.usecases.SetSharedPreferences
import dagger.Module
import dagger.Provides

@Module
interface ChangeNameDataModule {
    companion object {
        @Provides
        fun provideSetSharedPreferences(
            storage: SharedPreferences
        ): SetSharedPreferences<String> = SetSPUserRepository(storage)

        @Provides
        fun provideGetSharedPreferences(
            storage: SharedPreferences
        ): GetSharedPreferences<String> = GetSPUserRepository(storage)
    }
}