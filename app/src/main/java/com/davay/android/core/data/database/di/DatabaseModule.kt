package com.davay.android.core.data.database.di

import android.content.Context
import androidx.room.Room
import com.davay.android.core.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "davay")
            .build()
}
