package com.davay.android.feature.changename.di

import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.impl.GetUserDataUseCaseImpl
import com.davay.android.core.domain.impl.SetUserDataUseCaseImpl
import com.davay.android.core.domain.usecases.GetUserDataUseCase
import com.davay.android.core.domain.usecases.SetUserDataUseCase
import com.davay.android.feature.changename.domain.api.ChangeNameRepository
import com.davay.android.feature.changename.domain.api.usecase.SetToNetworkUsernameUseCase
import dagger.Module
import dagger.Provides

@Module
interface ChangeNameUseCaseModule {
    companion object {
        @Provides
        fun provideSetUserDataUseCase(
            repository: UserDataRepository
        ): SetUserDataUseCase = SetUserDataUseCaseImpl(repository)

        @Provides
        fun provideGetUserDataUseCase(
            repository: UserDataRepository
        ): GetUserDataUseCase = GetUserDataUseCaseImpl(repository)

        @Provides
        fun provideSetToNetworkUserNameUseCase(
            repository: ChangeNameRepository
        ) = SetToNetworkUsernameUseCase(repository)
    }
}