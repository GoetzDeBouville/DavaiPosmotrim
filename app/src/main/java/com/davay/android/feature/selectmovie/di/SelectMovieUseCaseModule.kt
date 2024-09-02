package com.davay.android.feature.selectmovie.di

import com.davay.android.feature.selectmovie.domain.FilterDislikedMovieListUseCase
import com.davay.android.feature.selectmovie.domain.GetMovieIdListSizeUseCase
import com.davay.android.feature.selectmovie.domain.GetMovieListUseCase
import com.davay.android.feature.selectmovie.domain.SwipeMovieUseCase
import com.davay.android.feature.selectmovie.domain.api.SelectMovieRepository
import dagger.Module
import dagger.Provides

@Module
class SelectMovieUseCaseModule {
    @Provides
    fun provideSwipeMovieUseCase(repository: SelectMovieRepository) =
        SwipeMovieUseCase(repository)

    @Provides
    fun provideFilterDislikedMovieListUseCase(repository: SelectMovieRepository) =
        FilterDislikedMovieListUseCase(repository)

    @Provides
    fun provideGetMovieIdListSizeUseCase(repository: SelectMovieRepository) =
        GetMovieIdListSizeUseCase(repository)

    @Provides
    fun provideGetMovieListUseCase(repository: SelectMovieRepository) =
        GetMovieListUseCase(repository)
}