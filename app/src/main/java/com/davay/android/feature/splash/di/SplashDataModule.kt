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
    fun bindSplashOnBoardingRepository(
        firstTimeFlagStorage: FirstTimeFlagStorage
    ): SplashOnBoardingRepository = SplashOnBoardingRepositoryImpl(firstTimeFlagStorage)

    @Provides
    fun bindSplashOnBoardingInteractor(
        repository: SplashOnBoardingRepository
    ): SplashOnBoardingInteractror = SplashOnBoardingInteractorImpl(repository)

    @Provides
    fun bindIsFirstTimeStorage(sharedPreferences: SharedPreferences): FirstTimeFlagStorage =
        FirstTimeFlagStorageImpl(sharedPreferences)

    @Provides
    fun bindSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
        "sharedPreferences",
        Context.MODE_PRIVATE
    )
}