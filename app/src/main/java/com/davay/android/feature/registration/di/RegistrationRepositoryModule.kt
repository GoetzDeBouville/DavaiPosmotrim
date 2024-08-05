package com.davay.android.feature.registration.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.core.data.impl.UserDataRepositoryImpl
import com.davay.android.core.data.network.HttpKtorNetworkClient
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.di.prefs.marker.StorageMarker
import com.davay.android.di.prefs.model.PreferencesStorage
import com.davay.android.feature.registration.data.RegistrationRepositoryImpl
import com.davay.android.feature.registration.data.network.HttpRegistrationKtorClient
import com.davay.android.feature.registration.data.network.RegistrationRequest
import com.davay.android.feature.registration.data.network.RegistrationResponse
import com.davay.android.feature.registration.domain.api.RegistrationRepository
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient

@Module
interface RegistrationRepositoryModule {
    companion object {
        @Provides
        fun provideRegistrationHttpNetworkClient(
            context: Context,
            httpClient: HttpClient
        ): HttpKtorNetworkClient<RegistrationRequest, RegistrationResponse> {
            return HttpRegistrationKtorClient(context, httpClient)
        }

        @Provides
        fun provideRegistrationRepository(
            httpNetworkClient: HttpKtorNetworkClient<RegistrationRequest, RegistrationResponse>,
            userDataRepository: UserDataRepository
        ): RegistrationRepository {
            return RegistrationRepositoryImpl(
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
}