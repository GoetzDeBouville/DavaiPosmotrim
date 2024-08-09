package com.davay.android.feature.registration.di

import com.davay.android.feature.registration.domain.api.RegistrationRepository
import com.davay.android.feature.registration.domain.usecase.RegistrationUseCase
import dagger.Module
import dagger.Provides

@Module
class RegistrationUseCaseModule {
    @Provides
    fun provideRegistrationUseCase(repository: RegistrationRepository) =
        RegistrationUseCase(repository)
}