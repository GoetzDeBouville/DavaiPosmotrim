package com.davay.android.di

import com.davay.android.core.data.dto.SessionResultDto
import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.data.impl.WebsocketRepositoryImpl
import com.davay.android.core.data.network.WebsocketMovieIdClient
import com.davay.android.core.data.network.WebsocketNetworkClient
import com.davay.android.core.data.network.WebsocketSessionResultClient
import com.davay.android.core.data.network.WebsocketSessionStatusClient
import com.davay.android.core.data.network.WebsocketUsersClient
import com.davay.android.core.domain.api.WebsocketRepository
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
class CommonWebsocketModule {

    @Provides
    fun provideWebsocketUsersClient(): WebsocketNetworkClient<List<UserDto>?> {
        return WebsocketUsersClient()
    }

    @Provides
    fun provideWebsocketSessionResultClient(): WebsocketNetworkClient<SessionResultDto?> {
        return WebsocketSessionResultClient()
    }

    @Provides
    fun provideWebsocketSessionStatusClient(): WebsocketNetworkClient<SessionStatusDto?> {
        return WebsocketSessionStatusClient()
    }

    @RouletteIdClient
    @Provides
    fun provideWebsocketRouletteIdClient(): WebsocketNetworkClient<Int?> {
        return WebsocketMovieIdClient()
    }

    @MatchesIdClient
    @Provides
    fun provideWebsocketMatchesIdClient(): WebsocketNetworkClient<Int?> {
        return WebsocketMovieIdClient()
    }

    @Provides
    fun provideWebsocketRepository(
        websocketUsersClient: WebsocketNetworkClient<List<UserDto>?>,
        websocketSessionResultClient: WebsocketNetworkClient<SessionResultDto?>,
        websocketSessionStatusClient: WebsocketNetworkClient<SessionStatusDto?>,
        @RouletteIdClient websocketRouletteIdClient: WebsocketNetworkClient<Int?>,
        @MatchesIdClient websocketMatchesIdClient: WebsocketNetworkClient<Int?>,
    ): WebsocketRepository {
        return WebsocketRepositoryImpl(
            websocketUsersClient,
            websocketSessionResultClient,
            websocketSessionStatusClient,
            websocketRouletteIdClient,
            websocketMatchesIdClient
        )
    }

    @Provides
    fun provideCommonWebsocketInteractor(
        websocketRepository: WebsocketRepository,
    ): CommonWebsocketInteractor {
        return CommonWebsocketInteractor(
            websocketRepository
        )
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RouletteIdClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MatchesIdClient
