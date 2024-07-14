package com.davay.android.feature.main.di

import android.content.SharedPreferences
import com.davay.android.data.impl.GetUserIdRepositoryImpl
import com.davay.android.domain.usecases.GetDataByKeyUseCase
import dagger.Module
import dagger.Provides

@Module
interface MainDataModule {
    companion object {
        @Provides
        fun provideGetSharedPreferences(
            storage: SharedPreferences
        ): GetDataByKeyUseCase<String> = GetUserIdRepositoryImpl(storage)
    }
}