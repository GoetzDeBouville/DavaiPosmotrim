package com.davay.android.feature.matchedsession.di

import com.davay.android.feature.matchedsession.domain.SessionWithMoviesRepository
import com.davay.android.feature.matchedsession.domain.SessionWithMoviesUseCase
import dagger.Module
import dagger.Provides

@Module
class MatchedSessionUseCaseModule {
    @Provides
    fun provideSessionWithMoviesUseCase(sessionWithMoviesRepository: SessionWithMoviesRepository):
        SessionWithMoviesUseCase = SessionWithMoviesUseCase(sessionWithMoviesRepository)
}