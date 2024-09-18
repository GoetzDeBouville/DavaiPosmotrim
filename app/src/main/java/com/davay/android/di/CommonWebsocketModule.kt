package com.davay.android.di

import android.content.SharedPreferences
import android.util.Log
import com.davay.android.core.data.dto.SessionResultDto
import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.data.impl.UserDataRepositoryImpl
import com.davay.android.core.data.impl.WebsocketRepositoryImpl
import com.davay.android.core.data.network.WebsocketMovieIdClient
import com.davay.android.core.data.network.WebsocketNetworkClient
import com.davay.android.core.data.network.WebsocketSessionResultClient
import com.davay.android.core.data.network.WebsocketSessionStatusClient
import com.davay.android.core.data.network.WebsocketUsersClient
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.api.WebsocketRepository
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.di.prefs.marker.StorageMarker
import com.davay.android.di.prefs.model.PreferencesStorage
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class CommonWebsocketModule {

    @Provides
    fun provideWebsocketUsersClient(): WebsocketNetworkClient<List<UserDto>?> {
        return WebsocketUsersClient().also { Log.d("MyTag", it.toString()) }
    }

    @Provides
    fun provideWebsocketSessionResultClient(): WebsocketNetworkClient<SessionResultDto?> {
        return WebsocketSessionResultClient().also { Log.d("MyTag", it.toString()) }
    }

    @Provides
    fun provideWebsocketSessionStatusClient(): WebsocketNetworkClient<SessionStatusDto?> {
        return WebsocketSessionStatusClient().also { Log.d("MyTag", it.toString()) }
    }

    @RouletteIdClient
    @Provides
    fun provideWebsocketRouletteIdClient(): WebsocketNetworkClient<Int?> {
        return WebsocketMovieIdClient()
    }

    @MatchesIdClient
    @Provides
    fun provideWebsocketMatchesIdClient(): WebsocketNetworkClient<Int?> {
        return WebsocketMovieIdClient().also { Log.d("MyTag", it.toString()) }
    }

    @Suppress("LongParameterList")
    @Provides
    fun provideWebsocketRepository(
        websocketUsersClient: WebsocketNetworkClient<List<UserDto>?>,
        websocketSessionResultClient: WebsocketNetworkClient<SessionResultDto?>,
        websocketSessionStatusClient: WebsocketNetworkClient<SessionStatusDto?>,
        @RouletteIdClient websocketRouletteIdClient: WebsocketNetworkClient<Int?>,
        @MatchesIdClient websocketMatchesIdClient: WebsocketNetworkClient<Int?>,
        userDataRepository: UserDataRepository,
    ): WebsocketRepository {
        return WebsocketRepositoryImpl(
            websocketUsersClient,
            websocketSessionResultClient,
            websocketSessionStatusClient,
            websocketRouletteIdClient,
            websocketMatchesIdClient,
            userDataRepository,
        ).also { Log.d("MyTag", it.toString()) }
    }

    @Provides
    fun provideUserDataRepository(
        @StorageMarker(PreferencesStorage.USER)
        storage: SharedPreferences
    ): UserDataRepository = UserDataRepositoryImpl(storage).also { Log.d("MyTag", it.toString()) }

    @Provides
    @Singleton
    fun provideCommonWebsocketInteractor(
        websocketRepository: WebsocketRepository,
    ): CommonWebsocketInteractor {
        return CommonWebsocketInteractor(
            websocketRepository
        ).also { Log.d("MyTag", it.toString()) }
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RouletteIdClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MatchesIdClient
