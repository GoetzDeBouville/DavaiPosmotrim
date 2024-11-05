package com.davay.android.core.presentation

import android.app.Application
import com.davay.android.di.AppComponentHolder

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponentHolder.createComponent(this)
    }
}