package com.davay.android.feature.waitsession.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.core.data.database.AppDatabase
import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagRepository
import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagStorage
import com.davay.android.feature.waitsession.data.WaitSessionOnBoardingRepositoryImpl
import com.davay.android.feature.waitsession.data.WaitSessionRepositoryImpl
import com.davay.android.feature.waitsession.data.WaitSessionStorageImpl
import com.davay.android.feature.waitsession.domain.SaveMovieIdListToDbUseCase
import com.davay.android.feature.waitsession.domain.api.WaitSessionOnBoardingInteractor
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractorImpl
import com.davay.android.feature.waitsession.domain.api.WaitSessionRepository
import dagger.Module
import dagger.Provides

@Module
class WaitSessionDataModule {
    @Provides
    fun provideWaitSessionOnBoardingRepository(
        firstTimeFlagStorage: FirstTimeFlagStorage
    ): FirstTimeFlagRepository = WaitSessionOnBoardingRepositoryImpl(firstTimeFlagStorage)

    @Provides
    fun provideWaitSessionRepository(
        appDatabase: AppDatabase
    ) : WaitSessionRepository = WaitSessionRepositoryImpl(appDatabase.movieIdDao())

    @Provides
    fun provideSaveMovieIdListToDbUseCase(
        repository: WaitSessionRepository
    ) = SaveMovieIdListToDbUseCase(repository)

    @Provides
    fun provideWaitSessionOnBoardingInteractor(
        repository: FirstTimeFlagRepository
    ): WaitSessionOnBoardingInteractor = WaitSessionOnBoardingInteractorImpl(repository)

    @Provides
    fun provideIsFirstTimeStorage(
        sharedPreferences: SharedPreferences
    ): FirstTimeFlagStorage = WaitSessionStorageImpl(sharedPreferences)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
        FirstTimeFlagStorage.STORAGE_NAME,
        Context.MODE_PRIVATE
    )
}