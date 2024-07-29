package com.davay.android.feature.changename.di

import android.content.SharedPreferences
import com.davay.android.core.data.impl.UserDataRepositoryImpl
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.impl.GetUserDataUseCaseImpl
import com.davay.android.core.domain.impl.SetUserDataUseCaseImpl
import com.davay.android.core.domain.usecases.GetUserDataUseCase
import com.davay.android.core.domain.usecases.SetUserDataUseCase
import com.davay.android.di.prefs.marker.StorageMarker
import com.davay.android.di.prefs.model.PreferencesStorage
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