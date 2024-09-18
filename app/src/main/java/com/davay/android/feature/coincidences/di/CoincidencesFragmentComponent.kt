package com.davay.android.feature.coincidences.di

import com.davay.android.di.AppComponent
import com.davay.android.di.FragmentScope
import com.davay.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AppComponent::class],
    modules = [
        CoincidencesFragmentModule::class,
        CoincidencesDataModule::class
    ]
)
@FragmentScope
interface CoincidencesFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): CoincidencesFragmentComponent

    }
}