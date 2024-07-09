package com.davay.android.feature.main.di

import com.davay.android.app.AppComponent
import com.davay.android.di.ScreenComponent
import com.davay.android.userData.UserDataModule
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [MainFragmentModule::class, UserDataModule::class]
)
interface MainFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): MainFragmentComponent
    }
}
