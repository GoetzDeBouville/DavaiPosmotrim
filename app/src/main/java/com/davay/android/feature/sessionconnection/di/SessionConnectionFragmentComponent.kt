package com.davay.android.feature.sessionconnection.di

import com.davay.android.di.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [SessionConnectionFragmentModule::class]
)
interface SessionConnectionFragmentComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): SessionConnectionFragmentComponent
    }
}