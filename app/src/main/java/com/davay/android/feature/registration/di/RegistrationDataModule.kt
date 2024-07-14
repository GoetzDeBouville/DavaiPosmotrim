package com.davay.android.feature.registration.di

import android.content.SharedPreferences
import com.davay.android.data.impl.SetUserIdRepositoryImpl
import com.davay.android.data.impl.SetUserNameRepositoryImpl
import com.davay.android.domain.impl.SetUserIdUseCaseImpl
import com.davay.android.domain.impl.SetUserNameUseCaseImpl
import com.davay.android.domain.repositories.SetUserIdRepository
import com.davay.android.domain.repositories.SetUserNameRepository
import com.davay.android.domain.usecases.SetSingleDataUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

enum class DataType {
    USER_NAME, USER_ID
}
@Qualifier
annotation class Type(val type: DataType)

@Module
interface RegistrationDataModule {
    companion object {
        @Provides
        fun provideSetUserIdRepository(
            storage: SharedPreferences
        ): SetUserIdRepository = SetUserIdRepositoryImpl(storage)

        @Type(DataType.USER_ID)
        @Provides
        fun provideSetUserIdUseCase(
            repository: SetUserIdRepository
        ): SetSingleDataUseCase<String> = SetUserIdUseCaseImpl(repository)

        @Provides
        fun provideSetUserNameRepository(
            storage: SharedPreferences
        ): SetUserNameRepository = SetUserNameRepositoryImpl(storage)

        @Type(DataType.USER_NAME)
        @Provides
        fun provideSetUserNameUseCase(
            repository: SetUserNameRepository
        ): SetSingleDataUseCase<String> = SetUserNameUseCaseImpl(repository)
    }
}