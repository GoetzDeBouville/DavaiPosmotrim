package com.davay.android.feature.main.di

import android.content.SharedPreferences
import com.davay.android.data.impl.GetUserNameRepositoryImpl
import com.davay.android.domain.impl.GetUserNameUseCaseImpl
import com.davay.android.domain.repositories.GetUserNameRepository
import com.davay.android.domain.usecases.GetSingleDataUseCase
import dagger.Module
import dagger.Provides

@Module
interface MainDataModule {
    companion object {
        @Provides
        fun provideGetUserNameRepository(
            storage: SharedPreferences
        ): GetUserNameRepository = GetUserNameRepositoryImpl(storage)

        @Provides
        fun provideGetUserNameUseCase(
            repository: GetUserNameRepository
        ): GetSingleDataUseCase<String> = GetUserNameUseCaseImpl(repository)
    }
}