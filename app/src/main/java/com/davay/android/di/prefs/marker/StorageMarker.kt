package com.davay.android.di.prefs.marker

import com.davay.android.di.prefs.model.PreferencesStorage
import javax.inject.Qualifier

@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE,
    AnnotationTarget.VALUE_PARAMETER
)
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StorageMarker(val value: PreferencesStorage)
