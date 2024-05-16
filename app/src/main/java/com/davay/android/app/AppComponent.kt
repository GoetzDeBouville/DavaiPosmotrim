package com.davay.android.app

import android.app.Application
import com.davay.android.di.ComponentHolderMode
import com.davay.android.di.DIComponent
import com.davay.android.di.DataBasedComponentHolder
import com.davay.android.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit

@Component(
    modules = [NetworkModule::class]
)
interface AppComponent : DIComponent {
    val retrofit: Retrofit

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(app: Application): Builder
    }
}

object AppComponentHolder : DataBasedComponentHolder<AppComponent, Application>() {
    override val mode: ComponentHolderMode = ComponentHolderMode.GLOBAL_SINGLETON
    override fun buildComponent(data: Application): AppComponent =
        DaggerAppComponent.builder().app(data).build()
}
