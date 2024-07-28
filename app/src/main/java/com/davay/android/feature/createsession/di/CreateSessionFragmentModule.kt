package com.davay.android.feature.createsession.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.davay.android.di.ViewModelKey
import com.davay.android.feature.createsession.data.network.HttpCreateSessionKtorClient
import com.davay.android.feature.createsession.presentation.compilations.CompilationsViewModel
import com.davay.android.feature.createsession.presentation.createsession.CreateSessionViewModel
import com.davay.android.feature.createsession.presentation.genre.GenreViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.ktor.client.HttpClient

@Module(includes = [CreateSessionFragmentModule.CreateSessionApiModule::class])
interface CreateSessionFragmentModule {

    @IntoMap
    @ViewModelKey(CreateSessionViewModel::class)
    @Binds
    fun bindCreateSessionViewModel(impl: CreateSessionViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CompilationsViewModel::class)
    @Binds
    fun bindCompilationsViewModel(impl: CompilationsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(GenreViewModel::class)
    @Binds
    fun bindGenreViewModel(impl: GenreViewModel): ViewModel

    @Module
    class CreateSessionApiModule {
        fun provideCreateSessionKtorClient(
            context: Context,
            httpClient: HttpClient
        ) = HttpCreateSessionKtorClient(context, httpClient)
    }
}
