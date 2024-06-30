package com.davay.android.feature.splash.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.feature.splash.data.IsFirstTimeStorage
import com.davay.android.feature.splash.data.OnBoardingRepositoryImpl
import com.davay.android.feature.splash.domain.OnBoardingInteractorImpl
import com.davay.android.feature.splash.domain.OnBoardingInteractror
import com.davay.android.feature.splash.domain.OnBoardingRepository
import dagger.Module
import dagger.Provides

@Module
class SplashDataModule {
    @Provides
    fun onBoardingRepository(isFirstTimeStorage: IsFirstTimeStorage): OnBoardingRepository = OnBoardingRepositoryImpl(isFirstTimeStorage)

    @Provides
    fun onBoardingInteractor(repository: OnBoardingRepository): OnBoardingInteractror = OnBoardingInteractorImpl(repository)

    @Provides
    fun isFirstTimeStorage(sharedPreferences: SharedPreferences): IsFirstTimeStorage = IsFirstTimeStorage(sharedPreferences)

    @Provides
    fun shared(context: Context): SharedPreferences = context.getSharedPreferences("sharedPreferences",
        Context.MODE_PRIVATE)
}