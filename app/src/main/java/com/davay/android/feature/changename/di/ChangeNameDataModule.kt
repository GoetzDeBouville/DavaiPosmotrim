package com.davay.android.feature.changename.di

import android.content.SharedPreferences
import com.davay.android.core.di.storage.marker.StorageMarker
import com.davay.android.core.di.storage.model.PreferencesStorage
import com.davay.android.data.impl.UserDataRepositoryImpl
import com.davay.android.domain.impl.GetUserDataUseCaseImpl
import com.davay.android.domain.impl.SetUserDataUseCaseImpl
import com.davay.android.domain.repositories.UserDataRepository
import com.davay.android.domain.usecases.GetUserDataUseCase
import com.davay.android.domain.usecases.SetUserDataUseCase
import dagger.Module
import dagger.Provides

@Module
interface ChangeNameDataModule {
    companion object {
        @Provides
        fun provideUserDataRepository(
            @StorageMarker(PreferencesStorage.USER)
            storage: SharedPreferences
        ): UserDataRepository = UserDataRepositoryImpl(storage)

        @Provides
        fun provideSetUserDataUseCase(
            repository: UserDataRepository
        ): SetUserDataUseCase = SetUserDataUseCaseImpl(repository)

        @Provides
        fun provideGetUserDataUseCase(
            repository: UserDataRepository
        ): GetUserDataUseCase = GetUserDataUseCaseImpl(repository)
    }
}