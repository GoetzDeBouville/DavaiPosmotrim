package com.davay.android.feature.registration.di

import android.content.SharedPreferences
import com.davay.android.base.repositories.SetSPUserRepository
import com.davay.android.base.usecases.SetDataByKeyUseCase
import dagger.Module
import dagger.Provides

@Module
interface RegistrationDataModule {
    companion object {
        @Provides
        fun provideSetSharedPreferences(
            storage: SharedPreferences
        ): SetDataByKeyUseCase<String> = SetSPUserRepository(storage)
    }
}