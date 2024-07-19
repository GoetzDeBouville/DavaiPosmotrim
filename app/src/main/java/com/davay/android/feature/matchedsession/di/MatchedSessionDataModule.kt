package com.davay.android.feature.matchedsession.di

import android.content.Context
import com.davay.android.domain.models.ErrorType
import com.davay.android.domain.models.MovieDetails
import com.davay.android.domain.usecases.GetData
import com.davay.android.feature.coincidences.data.TestMovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

const val GET_TEST_MOVIE_USE_CASE = "GET_TEST_MOVIE_USE_CASE"
@Module
class MatchedSessionsDataModule {

    @Provides
    fun testMovieRepository(context: Context): TestMovieRepository =
        TestMovieRepository(context)

    @Provides
    @Named(GET_TEST_MOVIE_USE_CASE)
    fun getTestMovieUseCase(repo: TestMovieRepository): GetData<MovieDetails, ErrorType> = repo
}