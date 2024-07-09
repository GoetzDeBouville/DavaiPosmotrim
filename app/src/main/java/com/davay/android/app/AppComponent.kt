package com.davay.android.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.davay.android.di.ComponentHolderMode
import com.davay.android.di.ContextModule
import com.davay.android.di.DIComponent
import com.davay.android.di.DataBasedComponentHolder
import com.davay.android.di.EncryptedSharedPreferencesModule
import com.davay.android.network.NetworkModule
import com.davay.android.utils.ConnectionChecker
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit

@Component(
    modules = [NetworkModule::class, ContextModule::class, EncryptedSharedPreferencesModule::class]
)
interface AppComponent : DIComponent {
    val retrofit: Retrofit
    val context: Context
    val encryptedSharedPreferences: SharedPreferences
    val connectionChecker: ConnectionChecker

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(app: Application): Builder

        fun contextModule(contextModule: ContextModule): Builder

        fun encryptedSharedPreferencesModule(encryptedSharedPreferencesModule: EncryptedSharedPreferencesModule): Builder
    }
}

object AppComponentHolder : DataBasedComponentHolder<AppComponent, Application>() {
    override val mode: ComponentHolderMode = ComponentHolderMode.GLOBAL_SINGLETON
    override fun buildComponent(data: Application): AppComponent =
        DaggerAppComponent.builder().contextModule(ContextModule(data)).app(data).build()
}
