package com.davay.android.feature.createsession.di

import com.davay.android.di.AppComponent
import com.davay.android.di.FragmentScope
import com.davay.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AppComponent::class],
    modules = [
        CreateSessionUseCaseModule::class,
        CreateSessionDataModule::class,
        CreateSessionFragmentModule::class
    ]
)
@FragmentScope
interface CreateSessionFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): CreateSessionFragmentComponent
    }
}
