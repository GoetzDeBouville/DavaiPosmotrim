package com.davay.android.app

import android.app.Application
import android.content.Context
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
        ContextModule::class
    ]
)
interface AppComponent : DIComponent {
    val client: HttpClient
    val context: Context

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
        DaggerAppComponent.builder().app(data).build()
}
