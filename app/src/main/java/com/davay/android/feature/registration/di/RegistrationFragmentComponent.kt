package com.davay.android.feature.registration.di

import com.davay.android.app.AppComponent
import com.davay.android.di.ScreenComponent
import com.davay.android.userdata.UserDataModule
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [RegistrationFragmentModule::class, UserDataModule::class]
)
interface RegistrationFragmentComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): RegistrationFragmentComponent
    }
}
