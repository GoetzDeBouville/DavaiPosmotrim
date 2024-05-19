package com.davay.android.feature.changename.di

import com.davay.android.app.AppComponent
import com.davay.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ChangeNameFragmentModule::class]
)
interface ChangeNameFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): ChangeNameFragmentComponent
    }
}
