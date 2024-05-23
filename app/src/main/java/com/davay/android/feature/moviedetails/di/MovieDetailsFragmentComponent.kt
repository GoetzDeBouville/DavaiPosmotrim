package com.davay.android.feature.moviedetails.di

import com.davay.android.app.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [MovieDetailsFragmentModule::class]
)
interface MovieDetailsFragmentComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): MovieDetailsFragmentComponent
    }
}