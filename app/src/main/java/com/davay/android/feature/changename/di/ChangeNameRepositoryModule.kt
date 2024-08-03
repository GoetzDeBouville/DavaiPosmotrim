package com.davay.android.feature.changename.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.core.data.impl.UserDataRepositoryImpl
import com.davay.android.core.data.network.HttpKtorNetworkClient
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.di.prefs.marker.StorageMarker
import com.davay.android.di.prefs.model.PreferencesStorage
import com.davay.android.feature.changename.data.ChangeNameRepositoryImpl
import com.davay.android.feature.changename.data.network.ChangeNameRequest
import com.davay.android.feature.changename.data.network.ChangeNameResponse
import com.davay.android.feature.changename.data.network.HttpChangeNameKtorClient
import com.davay.android.feature.changename.domain.api.ChangeNameRepository
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient

@Module
interface ChangeNameRepositoryModule {
    companion object {
        @Provides
        fun provideChangeNameHttpNetworkClient(
            context: Context,
            httpClient: HttpClient
        ): HttpKtorNetworkClient<ChangeNameRequest, ChangeNameResponse> {
            return HttpChangeNameKtorClient(context, httpClient)
        }

        @Provides
        fun provideChangeNameRepository(
            httpNetworkClient: HttpKtorNetworkClient<ChangeNameRequest, ChangeNameResponse>
        ): ChangeNameRepository {
            return ChangeNameRepositoryImpl(httpNetworkClient)
        }

        @Provides
        fun provideUserDataRepository(
            @StorageMarker(PreferencesStorage.USER)
            storage: SharedPreferences
        ): UserDataRepository = UserDataRepositoryImpl(storage)
    }
}