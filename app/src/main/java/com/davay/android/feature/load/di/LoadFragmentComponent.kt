package com.davay.android.feature.load.di

import com.davay.android.app.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [LoadFragmentModule::class]
)
interface LoadFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): LoadFragmentComponent
    }
}
