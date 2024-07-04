package com.davay.android.feature.matchedsession.di

import com.davay.android.app.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [MatchedSessionFragmentModule::class]
)
interface MatchedSessionFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): MatchedSessionFragmentComponent
    }
}
