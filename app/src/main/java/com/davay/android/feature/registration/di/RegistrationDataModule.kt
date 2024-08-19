package com.davay.android.feature.registration.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.core.data.impl.NetworkUserDataRepositoryImpl
import com.davay.android.core.data.impl.UserDataRepositoryImpl
import com.davay.android.core.data.network.HttpKtorNetworkClient
import com.davay.android.core.data.network.UserDataKtorNetworkClient
import com.davay.android.core.data.network.model.UserDataRequest
import com.davay.android.core.data.network.model.UserDataResponse
import com.davay.android.core.domain.api.NetworkUserDataRepository
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.di.prefs.marker.StorageMarker
import com.davay.android.di.prefs.model.PreferencesStorage
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient

@Module
class RegistrationDataModule {
    @Provides
    fun provideUserDataKtorNetworkClient(
        context: Context,
        httpClient: HttpClient
    ): HttpKtorNetworkClient<UserDataRequest, UserDataResponse> {
        return UserDataKtorNetworkClient(context, httpClient)
    }

    @Provides
    fun provideNetworkUserDataRepository(
        httpNetworkClient: HttpKtorNetworkClient<UserDataRequest, UserDataResponse>,
        userDataRepository: UserDataRepository
    ): NetworkUserDataRepository {
        return NetworkUserDataRepositoryImpl(
            httpNetworkClient = httpNetworkClient,
            userDataRepository = userDataRepository
        )
    }

    @Provides
    fun provideUserDataRepository(
        @StorageMarker(PreferencesStorage.USER)
        storage: SharedPreferences
    ): UserDataRepository = UserDataRepositoryImpl(storage)
}