package com.davay.android.feature.waitsession.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.core.data.impl.UserDataRepositoryImpl
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagRepository
import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagStorage
import com.davay.android.di.prefs.marker.StorageMarker
import com.davay.android.di.prefs.model.PreferencesStorage
import com.davay.android.feature.waitsession.data.WaitSessionOnBoardingRepositoryImpl
import com.davay.android.feature.waitsession.data.WaitSessionStorageImpl
import dagger.Module
import dagger.Provides

@Module
class WaitSessionDataModule {
    @Provides
    fun provideWaitSessionOnBoardingRepository(
        firstTimeFlagStorage: FirstTimeFlagStorage
    ): FirstTimeFlagRepository = WaitSessionOnBoardingRepositoryImpl(firstTimeFlagStorage)

    @Provides
    fun provideIsFirstTimeStorage(
        sharedPreferences: SharedPreferences
    ): FirstTimeFlagStorage = WaitSessionStorageImpl(sharedPreferences)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            FirstTimeFlagStorage.STORAGE_NAME, Context.MODE_PRIVATE
        )

    @Provides
    fun provideUserDataRepository(
        @StorageMarker(PreferencesStorage.USER) storage: SharedPreferences
    ): UserDataRepository = UserDataRepositoryImpl(storage)
}