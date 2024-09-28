package com.davay.android.feature.matchedsession.di

import com.davay.android.di.AppComponent
import com.davay.android.di.FragmentScope
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [
        MatchedSessionFragmentModule::class,
        MatchedSessionUseCaseModule::class
    ]
)
@FragmentScope
interface MatchedSessionFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): MatchedSessionFragmentComponent
    }
}
