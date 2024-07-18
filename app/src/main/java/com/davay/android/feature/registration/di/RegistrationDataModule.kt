package com.davay.android.feature.registration.di

import android.content.SharedPreferences
import com.davay.android.data.impl.UserDataRepositoryImpl
import com.davay.android.domain.impl.SetUserDataUseCaseImpl
import com.davay.android.domain.repositories.UserDataRepository
import com.davay.android.domain.usecases.SetUserDataUseCase
import dagger.Module
import dagger.Provides

@Module
interface RegistrationDataModule {
    companion object {
        @Provides
        fun provideUserDataRepository(
            storage: SharedPreferences
        ): UserDataRepository = UserDataRepositoryImpl(storage)

        @Provides
        fun provideSetUserUserDataUseCase(
            repository: UserDataRepository
        ): SetUserDataUseCase = SetUserDataUseCaseImpl(repository)
    }
}