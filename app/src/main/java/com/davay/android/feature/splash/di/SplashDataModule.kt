package com.davay.android.feature.splash.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.feature.splash.data.FirstTimeFlagStorage
import com.davay.android.feature.splash.data.SplashOnBoardingRepositoryImpl
import com.davay.android.feature.splash.domain.SplashOnBoardingInteractorImpl
import com.davay.android.feature.splash.domain.SplashOnBoardingInteractror
import com.davay.android.feature.splash.domain.SplashOnBoardingRepository
import dagger.Module
import dagger.Provides

@Module
class SplashDataModule {
    @Provides
    fun onBoardingRepository(
        firstTimeFlagStorage: FirstTimeFlagStorage
    ): SplashOnBoardingRepository =
        SplashOnBoardingRepositoryImpl(firstTimeFlagStorage)

    @Provides
    fun onBoardingInteractor(repository: SplashOnBoardingRepository): SplashOnBoardingInteractror =
        SplashOnBoardingInteractorImpl(repository)

    @Provides
    fun isFirstTimeStorage(sharedPreferences: SharedPreferences): FirstTimeFlagStorage =
        FirstTimeFlagStorage(sharedPreferences)

    @Provides
    fun sharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
        "sharedPreferences",
        Context.MODE_PRIVATE
    )
}