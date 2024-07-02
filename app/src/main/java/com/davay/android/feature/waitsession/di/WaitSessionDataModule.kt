package com.davay.android.feature.waitsession.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.feature.waitsession.data.FirstTimeFlagForWaitSessionStorageImpl
import com.davay.android.feature.waitsession.data.WaitSessionOnBoardingRepositoryImpl
import com.davay.android.feature.waitsession.domain.FirstTimeFlagForWaitSessionStorage
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractorImpl
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractror
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingRepository
import dagger.Module
import dagger.Provides

@Module
class WaitSessionDataModule {
    @Provides
    fun bindWaitSessionOnBoardingRepository(
        isNotFirstTimeStorage: FirstTimeFlagForWaitSessionStorage
    ): WaitSessionOnBoardingRepository = WaitSessionOnBoardingRepositoryImpl(isNotFirstTimeStorage)

    @Provides
    fun bindWaitSessionOnBoardingInteractor(
        repository: WaitSessionOnBoardingRepository
    ): WaitSessionOnBoardingInteractror = WaitSessionOnBoardingInteractorImpl(repository)

    @Provides
    fun bindIsFirstTimeStorage(
        sharedPreferences: SharedPreferences
    ): FirstTimeFlagForWaitSessionStorage = FirstTimeFlagForWaitSessionStorageImpl(sharedPreferences)

    @Provides
    fun bindSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
        "sharedPreferences",
        Context.MODE_PRIVATE
    )
}