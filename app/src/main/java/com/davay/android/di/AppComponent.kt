package com.davay.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.davay.android.core.di.storage.marker.StorageMarker
import com.davay.android.core.di.storage.model.PreferencesStorage
import com.davay.android.data.database.AppDatabase
import com.davay.android.data.di.DatabaseModule
import com.davay.android.data.di.NetworkModule
import com.davay.android.di.ComponentHolderMode
import com.davay.android.di.ContextModule
import com.davay.android.di.DIComponent
import com.davay.android.di.DataBasedComponentHolder
import com.davay.android.di.EncryptedSharedPreferencesModule
import dagger.BindsInstance
import dagger.Component
import io.ktor.client.HttpClient

@Component(
    modules = [
        NetworkModule::class,
        ContextModule::class,
        DatabaseModule::class,
        EncryptedSharedPreferencesModule::class
    ]
)
interface AppComponent : DIComponent {
    val retrofit: Retrofit
    val context: Context
    val dataBase: AppDatabase

    @StorageMarker(PreferencesStorage.USER)
    fun encryptedSharedPreferences(): SharedPreferences

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(app: Application): Builder

        fun contextModule(contextModule: ContextModule): Builder

        fun encryptedSharedPreferencesModule(
            encryptedSharedPreferencesModule: EncryptedSharedPreferencesModule
        ): Builder
    }
}

object AppComponentHolder : DataBasedComponentHolder<AppComponent, Application>() {
    override val mode: ComponentHolderMode = ComponentHolderMode.GLOBAL_SINGLETON
    override fun buildComponent(data: Application): AppComponent =
        DaggerAppComponent.builder()
            .app(data)
            .contextModule(ContextModule(data))
            .build()
}
