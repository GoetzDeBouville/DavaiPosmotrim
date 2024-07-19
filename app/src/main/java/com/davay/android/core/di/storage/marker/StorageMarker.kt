package com.davay.android.core.di.storage.marker

import com.davay.android.core.di.storage.model.PreferencesStorage
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
