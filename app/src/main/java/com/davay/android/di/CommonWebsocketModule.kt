package com.davay.android.di

import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.impl.SessionStatusWebsocketRepositoryImpl
import com.davay.android.core.data.network.WebsocketNetworkClient
import com.davay.android.core.data.network.WebsocketSessionStatusClient
import com.davay.android.core.domain.api.SessionStatusWebsocketRepository
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CommonWebsocketModule {

    @Provides
    @Named(SESSION_STATUS_CLIENT)
    fun provideWebsocketSessionStatusClient(sessionId: String):
            WebsocketNetworkClient<SessionStatusDto, SessionStatusDto> {
        return WebsocketSessionStatusClient(
            baseUrl = BASE_URL,
            path = "$sessionId$PATH_SESSION_STATUS"
        )
    }

    @Provides
    fun provideSessionStatusWebsocketRepository(
        @Named(SESSION_STATUS_CLIENT) client: WebsocketNetworkClient<SessionStatusDto, SessionStatusDto>
    ): SessionStatusWebsocketRepository {
        return SessionStatusWebsocketRepositoryImpl(client)
    }

    @Provides
    fun provideCommonWebsocketInteractor(
        sessionStatusWebsocketRepository: SessionStatusWebsocketRepository
    ): CommonWebsocketInteractor {
        return CommonWebsocketInteractor(sessionStatusWebsocketRepository)
    }

    companion object {
        const val BASE_URL = "ws://80.87.108.90/ws/session"
        const val PATH_SESSION_STATUS = "/session_status/"
        const val SESSION_STATUS_CLIENT = "SESSION_STATUS_CLIENT"
    }
}
