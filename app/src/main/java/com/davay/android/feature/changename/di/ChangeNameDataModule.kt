package com.davay.android.feature.changename.di

import android.content.SharedPreferences
import com.davay.android.data.impl.GetUserIdRepositoryImpl
import com.davay.android.data.impl.SetUserIdRepositoryImpl
import com.davay.android.domain.usecases.GetDataByKeyUseCase
import com.davay.android.domain.usecases.SetDataByKeyUseCase
import dagger.Module
import dagger.Provides

@Module
interface ChangeNameDataModule {
    companion object {
        @Provides
        fun provideSetSharedPreferences(
            storage: SharedPreferences
        ): SetDataByKeyUseCase<String> = SetUserIdRepositoryImpl(storage)

        @Provides
        fun provideGetSharedPreferences(
            storage: SharedPreferences
        ): GetDataByKeyUseCase<String> = GetUserIdRepositoryImpl(storage)
    }
}