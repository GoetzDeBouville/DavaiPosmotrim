package com.davay.android.feature.selectmovie.di

import android.content.Context
import com.davay.android.core.data.database.AppDatabase
import com.davay.android.core.data.network.HttpKtorNetworkClient
import com.davay.android.feature.selectmovie.data.network.impl.SelectMovieRepositoryImpl
import com.davay.android.feature.selectmovie.data.network.models.GetMovieRequest
import com.davay.android.feature.selectmovie.data.network.models.GetMovieResponse
import com.davay.android.feature.selectmovie.data.network.HttpGetMovieDetailsKtorClient
import com.davay.android.feature.selectmovie.domain.api.SelectMovieRepository
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient

@Module
class SelectMovieDataModule {
    @Provides
    fun provideGetMovieDetailsHttpNetworkClient(
        context: Context,
        httpClient: HttpClient
    ): HttpKtorNetworkClient<GetMovieRequest, GetMovieResponse> {
        return HttpGetMovieDetailsKtorClient(context, httpClient)
    }

    @Provides
    fun provideSelectMovieRepository(
        httpNetworkClient: HttpKtorNetworkClient<GetMovieRequest, GetMovieResponse>,
        appDatabase: AppDatabase
    ): SelectMovieRepository {
        return SelectMovieRepositoryImpl(
            httpNetworkClient,
            appDatabase.movieIdDao(),
            appDatabase.historyDao()
        )
    }
}