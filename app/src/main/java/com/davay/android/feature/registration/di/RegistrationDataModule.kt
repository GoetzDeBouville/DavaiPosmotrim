package com.davay.android.feature.registration.di

import android.content.SharedPreferences
import com.davay.android.core.data.impl.UserDataRepositoryImpl
import com.davay.android.core.domain.impl.SetUserDataUseCaseImpl
import com.davay.android.core.domain.repositories.UserDataRepository
import com.davay.android.core.domain.usecases.SetUserDataUseCase
import com.davay.android.di.storage.marker.StorageMarker
import com.davay.android.di.storage.model.PreferencesStorage
import dagger.Module
import dagger.Provides

@Module
interface RegistrationDataModule {
    companion object {
        @Provides
        fun provideUserDataRepository(
            @StorageMarker(PreferencesStorage.USER)
            storage: SharedPreferences
        ): UserDataRepository = UserDataRepositoryImpl(storage)

        @Provides
        fun provideSetUserUserDataUseCase(
            repository: UserDataRepository
        ): SetUserDataUseCase = SetUserDataUseCaseImpl(repository)
    }
}