package com.davay.android.feature.changename.di

import android.content.SharedPreferences
import com.davay.android.data.impl.GetUserNameRepositoryImpl
import com.davay.android.data.impl.SetUserNameRepositoryImpl
import com.davay.android.domain.impl.GetUserNameUseCaseImpl
import com.davay.android.domain.impl.SetUserNameUseCaseImpl
import com.davay.android.domain.repositories.GetUserNameRepository
import com.davay.android.domain.repositories.SetUserNameRepository
import com.davay.android.domain.usecases.GetSingleDataUseCase
import com.davay.android.domain.usecases.SetSingleDataUseCase
import dagger.Module
import dagger.Provides

@Module
interface ChangeNameDataModule {
    companion object {
        @Provides
        fun provideSetUserNameRepository(
            storage: SharedPreferences
        ): SetUserNameRepository = SetUserNameRepositoryImpl(storage)

        @Provides
        fun provideSetUserNameUseCase(
            repository: SetUserNameRepository
        ): SetSingleDataUseCase<String> = SetUserNameUseCaseImpl(repository)

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