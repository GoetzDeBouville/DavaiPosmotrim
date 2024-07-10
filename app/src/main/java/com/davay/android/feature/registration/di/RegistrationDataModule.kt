package com.davay.android.feature.registration.di

import android.content.SharedPreferences
import com.davay.android.base.repositories.SetSPUserRepository
import com.davay.android.base.usecases.SetSharedPreferences
import dagger.Module
import dagger.Provides

@Module
class RegistrationDataModule {
    @Provides
    fun provideSetSharedPreferences(
        storage: SharedPreferences
    ): SetSharedPreferences<String> = SetSPUserRepository(storage)
}