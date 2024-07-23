package com.davay.android.feature.createsession.di

import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import com.davay.android.feature.createsession.domain.api.GetCollectionsUseCase
import com.davay.android.feature.createsession.domain.api.GetGenresUseCase
import com.davay.android.feature.createsession.domain.impl.GetCollectionsUseCaseImpl
import com.davay.android.feature.createsession.domain.impl.GetGenresUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class CreateSessionUseCaseModule {
    @Provides
    fun provideGetCollectionsUseCase(repository: CreateSessionRepository): GetCollectionsUseCase =
        GetCollectionsUseCaseImpl(repository)

    @Provides
    fun provideGetGenresUseCase(repository: CreateSessionRepository): GetGenresUseCase =
        GetGenresUseCaseImpl(repository)
}
