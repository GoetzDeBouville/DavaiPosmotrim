package com.davay.android.feature.main.di

import android.content.SharedPreferences
import com.davay.android.data.impl.UserDataRepositoryImpl
import com.davay.android.domain.impl.GetUserDataUseCaseImpl
import com.davay.android.domain.repositories.UserDataRepository
import com.davay.android.domain.usecases.GetUserDataUseCase
import dagger.Module
import dagger.Provides

@Module
interface MainDataModule {
    companion object {
        @Provides
        fun provideGetUserDataRepository(
            storage: SharedPreferences
        ): UserDataRepository = UserDataRepositoryImpl(storage)

        @Provides
        fun provideGetUserDataUseCase(
            repository: UserDataRepository
        ): GetUserDataUseCase = GetUserDataUseCaseImpl(repository)
    }
}