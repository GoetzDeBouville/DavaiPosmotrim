package com.davay.android.feature.sessionconnection.presentation;

import javax.annotation.processing.Generated;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
        value = "dagger.internal.codegen.ComponentProcessor",
        comments = "https://dagger.dev"
)
@SuppressWarnings({
        "unchecked",
        "rawtypes",
        "KotlinInternal",
        "KotlinInternalInJava"
})
public final class SessionConnectionViewModel_Factory implements Factory<SessionConnectionViewModel> {
    @Override
    public SessionConnectionViewModel get() {
        return newInstance();
    }

    public static SessionConnectionViewModel_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SessionConnectionViewModel newInstance() {
        return new SessionConnectionViewModel();
    }

    private static final class InstanceHolder {
        private static final SessionConnectionViewModel_Factory INSTANCE = new SessionConnectionViewModel_Factory();
    }
}
