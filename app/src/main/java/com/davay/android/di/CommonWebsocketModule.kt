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
    fun provideWebsocketSessionStatusClient():
            WebsocketNetworkClient<SessionStatusDto, SessionStatusDto> {
        return WebsocketSessionStatusClient()
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
        const val SESSION_STATUS_CLIENT = "SESSION_STATUS_CLIENT"
    }
}
