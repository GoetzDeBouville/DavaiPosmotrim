package com.davay.android.feature.sessionsmatched.di

import com.davay.android.data.database.AppDatabase
import com.davay.android.feature.sessionsmatched.data.GetSessionsHistoryRepositoryImpl
import com.davay.android.feature.sessionsmatched.domain.GetSessionsHistoryRepository
import dagger.Module
import dagger.Provides

@Module
class MatchedSessionListDataModule {

    @Provides
    fun provideGetSessionsHistoryRepository(appDatabase: AppDatabase): GetSessionsHistoryRepository =
        GetSessionsHistoryRepositoryImpl(appDatabase.historyDao())
}
