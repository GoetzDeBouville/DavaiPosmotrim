package com.davay.android.feature.sessionsmatched.di

import com.davay.android.feature.sessionsmatched.domain.GetSessionsHistoryRepository
import com.davay.android.feature.sessionsmatched.domain.GetSessionsHistoryUseCase
import dagger.Module
import dagger.Provides

@Module
class MatchedSessionListUseCaseModule {
    @Provides
    fun provideGetSessionsHistoryUseCase(getSessionsHistoryRepository: GetSessionsHistoryRepository):
        GetSessionsHistoryUseCase = GetSessionsHistoryUseCase(getSessionsHistoryRepository)
}