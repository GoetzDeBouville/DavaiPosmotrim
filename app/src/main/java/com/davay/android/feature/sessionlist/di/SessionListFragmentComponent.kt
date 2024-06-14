package com.davay.android.feature.sessionlist.di

import com.davay.android.app.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [SessionListFragmentModule::class]
)
interface SessionListFragmentComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): SessionListFragmentComponent
    }
}