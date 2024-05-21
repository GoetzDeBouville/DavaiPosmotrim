package com.davay.android.feature.sessionconnection.di;

import androidx.lifecycle.ViewModel;

import com.davay.android.app.AppComponent;
import com.davay.android.app.ViewModelFactory;
import com.davay.android.feature.sessionconnection.presentation.SessionConnectionViewModel;
import com.davay.android.feature.sessionconnection.presentation.SessionConnectionViewModel_Factory;

import java.util.Collections;
import java.util.Map;

import javax.annotation.processing.Generated;
import javax.inject.Provider;

import dagger.internal.DaggerGenerated;
import dagger.internal.Preconditions;

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
public final class DaggerSessionConnectionFragmentComponent {
    private DaggerSessionConnectionFragmentComponent() {
    }

    public static SessionConnectionFragmentComponent.Builder builder() {
        return new Builder();
    }

    private static final class Builder implements SessionConnectionFragmentComponent.Builder {
        private AppComponent appComponent;

        @Override
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override
        public SessionConnectionFragmentComponent build() {
            Preconditions.checkBuilderRequirement(appComponent, AppComponent.class);
            return new SessionConnectionFragmentComponentImpl(appComponent);
        }
    }

    private static final class SessionConnectionFragmentComponentImpl implements SessionConnectionFragmentComponent {
        private final SessionConnectionFragmentComponentImpl registrationFragmentComponentImpl = this;

        private SessionConnectionFragmentComponentImpl(AppComponent appComponentParam) {


        }

        private Map<Class<? extends ViewModel>, Provider<ViewModel>> mapOfClassOfAndProviderOfViewModel(
        ) {
            return Collections.<Class<? extends ViewModel>, Provider<ViewModel>>singletonMap(SessionConnectionViewModel.class, ((dagger.internal.Provider) SessionConnectionViewModel_Factory.create()));
        }

        @Override
        public ViewModelFactory getViewModelFactory() {
            return new ViewModelFactory(mapOfClassOfAndProviderOfViewModel());
        }
    }
}
