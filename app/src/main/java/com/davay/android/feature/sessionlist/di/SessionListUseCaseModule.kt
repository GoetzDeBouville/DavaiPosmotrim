package com.davay.android.feature.sessionlist.di

import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.impl.GetUserDataUseCaseImpl
import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagRepository
import com.davay.android.core.domain.usecases.GetUserDataUseCase
import com.davay.android.feature.sessionlist.domain.api.ConnectToSessionRepository
import com.davay.android.feature.sessionlist.domain.usecase.ConnectToSessionUseCase
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractorImpl
import com.davay.android.feature.waitsession.domain.api.WaitSessionOnBoardingInteractor
import dagger.Module
import dagger.Provides

@Module
class SessionListUseCaseModule {
    @Provides
    fun provideWaitSessionOnBoardingInteractor(
        repository: FirstTimeFlagRepository
    ): WaitSessionOnBoardingInteractor = WaitSessionOnBoardingInteractorImpl(repository)

    @Provides
    fun provideConnectToSessionUseCase(repository: ConnectToSessionRepository) =
        ConnectToSessionUseCase(repository)

    @Provides
    fun provideGetUserDataUseCase(
        repository: UserDataRepository
    ): GetUserDataUseCase = GetUserDataUseCaseImpl(repository)
}