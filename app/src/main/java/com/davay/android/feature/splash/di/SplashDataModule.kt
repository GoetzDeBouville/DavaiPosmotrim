package com.davay.android.feature.splash.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.app.core.feature.introduction.domain.FirstTimeFlagRepository
import com.davay.android.app.core.feature.introduction.domain.FirstTimeFlagStorage
import com.davay.android.feature.splash.data.SplashOnBoardingRepositoryImpl
import com.davay.android.feature.splash.data.SplashStorageImpl
import com.davay.android.feature.splash.domain.SplashOnBoardingInteractorImpl
import com.davay.android.feature.splash.domain.SplashOnBoardingInteractror
import dagger.Module
import dagger.Provides

@Module
class SplashDataModule {
    @Provides
    fun provideSplashOnBoardingRepository(
        firstTimeFlagStorage: FirstTimeFlagStorage
    ): FirstTimeFlagRepository = SplashOnBoardingRepositoryImpl(firstTimeFlagStorage)

    @Provides
    fun provideSplashOnBoardingInteractor(
        repository: FirstTimeFlagRepository
    ): SplashOnBoardingInteractror = SplashOnBoardingInteractorImpl(repository)

    @Provides
    fun provideIsFirstTimeStorage(sharedPreferences: SharedPreferences): FirstTimeFlagStorage =
        SplashStorageImpl(sharedPreferences)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            FirstTimeFlagStorage.STORAGE_NAME,
            Context.MODE_PRIVATE
        )
}