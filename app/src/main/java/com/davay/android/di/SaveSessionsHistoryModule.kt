package com.davay.android.di

import com.davay.android.core.data.database.AppDatabase
import com.davay.android.core.data.impl.SaveSessionsHistoryRepositoryImpl
import com.davay.android.core.domain.api.SaveSessionsHistoryRepository
import dagger.Module
import dagger.Provides

@Module
class SaveSessionsHistoryModule {
    @Provides
    fun provideSaveSessionsHistoryRepository(
        appDatabase: AppDatabase
    ): SaveSessionsHistoryRepository {
        return SaveSessionsHistoryRepositoryImpl(appDatabase.historyDao())
    }
}