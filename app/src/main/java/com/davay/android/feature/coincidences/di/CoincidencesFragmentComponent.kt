package com.davay.android.feature.coincidences.di

import com.davay.android.app.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [CoincidencesFragmentModule::class]
)
interface CoincidencesFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): CoincidencesFragmentComponent

    }
}