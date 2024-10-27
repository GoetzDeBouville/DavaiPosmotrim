package com.davay.android.feature.sessionlist.di

import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.impl.GetUserDataUseCaseImpl
import com.davay.android.core.domain.usecases.GetUserDataUseCase
import com.davay.android.feature.sessionlist.domain.api.ConnectToSessionRepository
import com.davay.android.feature.sessionlist.domain.usecase.ConnectToSessionUseCase
import dagger.Module
import dagger.Provides

@Module
class SessionListUseCaseModule {
    @Provides
    fun provideConnectToSessionUseCase(repository: ConnectToSessionRepository) =
        ConnectToSessionUseCase(repository)

    @Provides
    fun provideGetUserDataUseCase(
        repository: UserDataRepository
    ): GetUserDataUseCase = GetUserDataUseCaseImpl(repository)
}