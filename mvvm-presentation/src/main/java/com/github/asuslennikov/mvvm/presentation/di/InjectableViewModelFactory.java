package com.github.asuslennikov.mvvm.presentation.di;

import com.github.asuslennikov.mvvm.api.presentation.ViewModel;
import com.github.asuslennikov.mvvm.presentation.ViewModelFactory;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Фабрика по созданию объектов {@link ViewModel} с помощью инжекции зависимостей (dagger 2).
 */
public final class InjectableViewModelFactory implements ViewModelFactory {
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> instances;

    @Inject
    public InjectableViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> instances) {
        this.instances = instances;
    }

    @Override
    public <VM extends ViewModel<?>> VM create(Class<VM> viewModelClass) {
        Provider<ViewModel> provider = instances.get(viewModelClass);
        if (provider == null) {
            return null;
        }
        return viewModelClass.cast(provider.get());
    }
}
