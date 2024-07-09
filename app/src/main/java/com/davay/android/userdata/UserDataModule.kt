package com.davay.android.userdata

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class UserDataModule {
    @Provides
    fun provideUserDataRepository(
        storage: SharedPreferences
    ): UserDataRepository = UserDataRepositoryImpl(storage)

    @Provides
    fun provideUserDataInteractor(
        repository: UserDataRepository
    ): UserDataInteractor = UserDataInteractorImpl(repository)
}