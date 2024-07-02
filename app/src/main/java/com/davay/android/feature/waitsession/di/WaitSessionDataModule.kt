package com.davay.android.feature.waitsession.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.feature.waitsession.data.FirstTimeFlagForWaitSessionStorage
import com.davay.android.feature.waitsession.data.WaitSessionOnBoardingRepositoryImpl
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractorImpl
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractror
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingRepository
import dagger.Module
import dagger.Provides

@Module
class WaitSessionDataModule {
    @Provides
    fun waitSessionOnBoardingRepository(
        isNotFirstTimeStorage: FirstTimeFlagForWaitSessionStorage
    ): WaitSessionOnBoardingRepository = WaitSessionOnBoardingRepositoryImpl(isNotFirstTimeStorage)

    @Provides
    fun waitSessionOnBoardingInteractor(
        repository: WaitSessionOnBoardingRepository
    ): WaitSessionOnBoardingInteractror = WaitSessionOnBoardingInteractorImpl(repository)

    @Provides
    fun isFirstTimeStorage(
        sharedPreferences: SharedPreferences
    ): FirstTimeFlagForWaitSessionStorage = FirstTimeFlagForWaitSessionStorage(sharedPreferences)

    @Provides
    fun sharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
        "sharedPreferences",
        Context.MODE_PRIVATE
    )
}