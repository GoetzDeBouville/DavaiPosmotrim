package com.davay.android.feature.sessionlist.di

import android.content.Context
import android.content.SharedPreferences
import com.davay.android.core.data.MovieIdListToDbSaver
import com.davay.android.core.data.MovieIdListToDbSaverImpl
import com.davay.android.core.data.database.AppDatabase
import com.davay.android.core.data.network.HttpKtorNetworkClient
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagRepository
import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagStorage
import com.davay.android.feature.sessionlist.data.ConnectToSessionRepositoryImpl
import com.davay.android.feature.sessionlist.data.network.ConnectToSessionRequest
import com.davay.android.feature.sessionlist.data.network.ConnectToSessionResponse
import com.davay.android.feature.sessionlist.data.network.HttpConnectToSessionKtorClient
import com.davay.android.feature.sessionlist.domain.api.ConnectToSessionRepository
import com.davay.android.feature.waitsession.data.impl.WaitSessionOnBoardingRepositoryImpl
import com.davay.android.feature.waitsession.data.impl.WaitSessionStorageImpl
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient

@Module
class SessionListDataModule {
    @Provides
    fun provideConnectToSessionHttpNetworkClient(
        context: Context,
        httpClient: HttpClient
    ): HttpKtorNetworkClient<ConnectToSessionRequest, ConnectToSessionResponse> {
        return HttpConnectToSessionKtorClient(context, httpClient)
    }

    @Provides
    fun provideConnectToSessionRepository(
        httpNetworkClient: HttpKtorNetworkClient<ConnectToSessionRequest, ConnectToSessionResponse>,
        userDataRepository: UserDataRepository,
        appDatabase: AppDatabase,
        movieIdListToDbSaver: MovieIdListToDbSaver
    ): ConnectToSessionRepository {
        return ConnectToSessionRepositoryImpl(
            appDatabase.movieIdDao(),
            userDataRepository,
            httpNetworkClient,
            movieIdListToDbSaver,
        )
    }

    @Provides
    fun provideMovieIdListToDbSaver(): MovieIdListToDbSaver {
        return MovieIdListToDbSaverImpl()
    }

    @Provides
    fun provideWaitSessionOnBoardingRepository(
        firstTimeFlagStorage: FirstTimeFlagStorage
    ): FirstTimeFlagRepository = WaitSessionOnBoardingRepositoryImpl(firstTimeFlagStorage)

    @Provides
    fun provideIsFirstTimeStorage(
        sharedPreferences: SharedPreferences
    ): FirstTimeFlagStorage = WaitSessionStorageImpl(sharedPreferences)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            FirstTimeFlagStorage.STORAGE_NAME,
            Context.MODE_PRIVATE
        )
}