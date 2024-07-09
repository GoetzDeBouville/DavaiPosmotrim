package com.davay.android.feature.registration.di

import android.content.SharedPreferences
import com.davay.android.feature.registration.data.UserDataRepositoryImpl
import com.davay.android.feature.registration.domain.UserDataInteractor
import com.davay.android.feature.registration.domain.UserDataInteractorImpl
import com.davay.android.feature.registration.domain.UserDataRepository
import dagger.Module
import dagger.Provides

@Module
class RegistrationDataModule {
    @Provides
    fun provideUserDataRepository(
        storage: SharedPreferences
    ): UserDataRepository = UserDataRepositoryImpl(storage)

    @Provides
    fun provideUserDataInteractor(
        repository: UserDataRepository
    ): UserDataInteractor = UserDataInteractorImpl(repository)
}