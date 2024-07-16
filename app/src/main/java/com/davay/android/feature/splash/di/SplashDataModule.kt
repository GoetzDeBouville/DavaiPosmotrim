package com.davay.android.feature.splash.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.feature.splash.data.FirstTimeFlagStorageImpl
import com.davay.android.feature.splash.data.SplashOnBoardingRepositoryImpl
import com.davay.android.feature.splash.domain.FirstTimeFlagStorage
import com.davay.android.feature.splash.domain.SplashOnBoardingInteractorImpl
import com.davay.android.feature.splash.domain.SplashOnBoardingInteractror
import com.davay.android.feature.splash.domain.SplashOnBoardingRepository
import dagger.Module
import dagger.Provides

@Module
class SplashDataModule {
    @Provides
    fun provideSplashOnBoardingRepository(
        firstTimeFlagStorage: FirstTimeFlagStorage
    ): SplashOnBoardingRepository = SplashOnBoardingRepositoryImpl(firstTimeFlagStorage)

    @Provides
    fun provideSplashOnBoardingInteractor(
        repository: SplashOnBoardingRepository
    ): SplashOnBoardingInteractror = SplashOnBoardingInteractorImpl(repository)

    @Provides
    fun provideIsFirstTimeStorage(sharedPreferences: SharedPreferences): FirstTimeFlagStorage =
        FirstTimeFlagStorageImpl(sharedPreferences)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            FirstTimeFlagStorage.STORAGE_NAME,
            Context.MODE_PRIVATE
        )
}