package com.davay.android.feature.selectmovie.di

import com.davay.android.core.data.database.AppDatabase
import com.davay.android.feature.selectmovie.data.SaveSessionsHistoryRepositoryImpl
import com.davay.android.feature.selectmovie.domain.SaveSessionsHistoryRepository
import dagger.Module
import dagger.Provides

@Module
class SelectMovieDataModule {
    @Provides
    fun provideSaveSessionsHistoryRepository(appDatabase: AppDatabase): SaveSessionsHistoryRepository =
        SaveSessionsHistoryRepositoryImpl(appDatabase.historyDao())
}