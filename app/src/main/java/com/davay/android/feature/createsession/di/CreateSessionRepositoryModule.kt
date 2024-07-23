package com.davay.android.feature.createsession.di

import com.davay.android.feature.createsession.data.CreateSessionRepositoryImpl
import com.davay.android.feature.createsession.data.network.CreateSessionApiService
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import dagger.Module
import dagger.Provides

@Module
class CreateSessionRepositoryModule {

    @Provides
    fun provideCreateSessionRepository(apiService: CreateSessionApiService): CreateSessionRepository =
        CreateSessionRepositoryImpl(apiService)
}
