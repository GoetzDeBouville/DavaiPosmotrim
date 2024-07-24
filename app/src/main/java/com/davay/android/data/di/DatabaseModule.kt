package com.davay.android.data.di

import android.content.Context
import androidx.room.Room
import com.davay.android.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "davay")
            .build()

    @Provides
    fun provideHistoryDao(appDatabase: AppDatabase) = appDatabase.historyDao()
}
