package com.davay.android.di

import android.app.Application
import android.content.Context
import com.davay.android.core.data.di.NetworkModule
import com.davay.android.data.database.AppDatabase
import com.davay.android.data.di.DatabaseModule
import com.davay.android.data.di.NetworkModule
import com.davay.android.di.ComponentHolderMode
import com.davay.android.di.ContextModule
import com.davay.android.di.DIComponent
import com.davay.android.di.DataBasedComponentHolder
import dagger.BindsInstance
import dagger.Component
import io.ktor.client.HttpClient

@Component(
    modules = [
        NetworkModule::class,
        ContextModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent : DIComponent {
    val client: HttpClient
    val context: Context
    val dataBase: AppDatabase

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(app: Application): Builder

        fun contextModule(contextModule: ContextModule): Builder
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
