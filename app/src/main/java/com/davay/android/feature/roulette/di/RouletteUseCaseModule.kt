package com.davay.android.feature.roulette.di

import com.davay.android.feature.roulette.domain.api.StartRouletteRepository
import com.davay.android.feature.roulette.domain.impl.StartRouletteUseCase
import dagger.Module
import dagger.Provides

@Module
class RouletteUseCaseModule {
    @Provides
    fun provideStartRouletteUseCase(repository: StartRouletteRepository) =
        StartRouletteUseCase(repository)
}