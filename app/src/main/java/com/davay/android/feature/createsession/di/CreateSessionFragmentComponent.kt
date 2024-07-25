package com.davay.android.feature.createsession.di

import com.davay.android.di.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [
        CreateSessionFragmentModule::class,
        CreateSessionRepositoryModule::class,
        CreateSessionUseCaseModule::class
    ]
)
interface CreateSessionFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): CreateSessionFragmentComponent
    }
}
