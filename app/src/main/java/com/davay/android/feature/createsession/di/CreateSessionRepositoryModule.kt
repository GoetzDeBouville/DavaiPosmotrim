package com.davay.android.feature.createsession.di

import android.content.Context
import com.davay.android.core.data.network.HttpKtorNetworkClient
import com.davay.android.feature.createsession.data.CreateSessionRepositoryImpl
import com.davay.android.feature.createsession.data.network.CreateSessionRequest
import com.davay.android.feature.createsession.data.network.CreateSessionResponse
import com.davay.android.feature.createsession.data.network.HttpCreateSessionKtorClient
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient

@Module
class CreateSessionRepositoryModule {

    @Provides
    fun provideCreateSessionHttpNetworkClient(
        context: Context,
        httpClient: HttpClient
    ): HttpKtorNetworkClient<CreateSessionRequest, CreateSessionResponse> {
        return HttpCreateSessionKtorClient(context, httpClient)
    }

    @Provides
    fun provideCreateSessionRepository(
        httpNetworkClient: HttpKtorNetworkClient<CreateSessionRequest, CreateSessionResponse>
    ): CreateSessionRepository {
        return CreateSessionRepositoryImpl(httpNetworkClient)
    }
}
