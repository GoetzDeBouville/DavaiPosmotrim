package com.davay.android.feature.matchedsession.di

import com.davay.android.core.data.database.AppDatabase
import com.davay.android.feature.matchedsession.data.SessionWithMoviesRepositoryImpl
import com.davay.android.feature.matchedsession.domain.SessionWithMoviesRepository
import dagger.Module
import dagger.Provides

@Module
class MatchedSessionDataModule {

    @Provides
    fun provideSessionWithMoviesRepository(appDatabase: AppDatabase): SessionWithMoviesRepository =
        SessionWithMoviesRepositoryImpl(appDatabase.historyDao())
}