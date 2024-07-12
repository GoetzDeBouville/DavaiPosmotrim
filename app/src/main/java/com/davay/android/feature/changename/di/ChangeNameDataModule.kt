package com.davay.android.feature.changename.di

import android.content.SharedPreferences
import com.davay.android.data.repositories.GetSPUserRepository
import com.davay.android.data.repositories.SetSPUserRepository
import com.davay.android.domain.usecases.GetDataByKeyUseCase
import com.davay.android.domain.usecases.SetDataByKeyUseCase
import dagger.Module
import dagger.Provides

@Module
interface ChangeNameDataModule {
    companion object {
        @Provides
        fun provideSetSharedPreferences(
            storage: SharedPreferences
        ): SetDataByKeyUseCase<String> = SetSPUserRepository(storage)

        @Provides
        fun provideGetSharedPreferences(
            storage: SharedPreferences
        ): GetDataByKeyUseCase<String> = GetSPUserRepository(storage)
    }
}