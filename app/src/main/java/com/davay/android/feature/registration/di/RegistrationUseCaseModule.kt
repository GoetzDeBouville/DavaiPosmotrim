package com.davay.android.feature.registration.di

import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.impl.SetUserDataUseCaseImpl
import com.davay.android.feature.registration.domain.api.RegistrationRepository
import com.davay.android.feature.registration.domain.usecase.SetToNetworkUserDataUseCase
import dagger.Module
import dagger.Provides

@Module
interface RegistrationUseCaseModule {
    companion object {
        @Provides
        fun provideRegistrationUseCase(repository: RegistrationRepository) =
            SetToNetworkUserDataUseCase(repository)

        @Provides
        fun provideSetUserUserDataUseCase(
            repository: UserDataRepository
        ): com.davay.android.core.domain.usecases.SetUserDataUseCase =
            SetUserDataUseCaseImpl(repository)
    }
}