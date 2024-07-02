package com.davay.android.feature.splash.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.feature.splash.data.FirstTimeFlagStorage
import com.davay.android.feature.splash.data.OnBoardingRepositoryImpl
import com.davay.android.feature.splash.domain.OnBoardingInteractorImpl
import com.davay.android.feature.splash.domain.OnBoardingInteractror
import com.davay.android.feature.splash.domain.OnBoardingRepository
import dagger.Module
import dagger.Provides

@Module
class SplashDataModule {
    @Provides
    fun onBoardingRepository(firstTimeFlagStorage: FirstTimeFlagStorage): OnBoardingRepository =
        OnBoardingRepositoryImpl(firstTimeFlagStorage)

    @Provides
    fun onBoardingInteractor(repository: OnBoardingRepository): OnBoardingInteractror =
        OnBoardingInteractorImpl(repository)

    @Provides
    fun isFirstTimeStorage(sharedPreferences: SharedPreferences): FirstTimeFlagStorage =
        FirstTimeFlagStorage(sharedPreferences)

    @Provides
    fun sharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
        "sharedPreferences",
        Context.MODE_PRIVATE
    )
}