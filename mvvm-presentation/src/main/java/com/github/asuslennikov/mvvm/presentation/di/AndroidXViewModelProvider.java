package com.github.asuslennikov.mvvm.presentation.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStoreOwner;

import com.github.asuslennikov.mvvm.api.presentation.State;
import com.github.asuslennikov.mvvm.api.presentation.ViewModel;
import com.github.asuslennikov.mvvm.presentation.ViewModelFactory;
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider;

import javax.inject.Inject;

/**
 * Данный класс представляет собой фабрику по созданию экземпляров {@link ViewModel}.
 * По своей логике - это простой lookup в таблицу с содержимым класс (ключ) - провайдер (значение).
 * Если в таблице не найдено значение для указанного класса (не найден провайдер), то
 * создание экземпляра производится через конструктор по-умолчанию (конструктор без аргументов).
 *
 * @see androidx.lifecycle.ViewModelProvider.NewInstanceFactory
 */
public final class AndroidXViewModelProvider extends androidx.lifecycle.ViewModelProvider.NewInstanceFactory implements ViewModelProvider {
    private final ViewModelFactory instanceProvider;

    @Inject
    public AndroidXViewModelProvider(ViewModelFactory instanceProvider) {
        this.instanceProvider = instanceProvider;
    }

    @NonNull
    @Override
    public <STATE extends State, VM extends ViewModel<STATE>> VM getViewModel(@NonNull ViewModelStoreOwner storeOwner, @NonNull Class<VM> viewModelClass) {
        androidx.lifecycle.ViewModel androidxViewModel = getAndroidXViewModel(storeOwner, checkAndCastClass(viewModelClass));
        return viewModelClass.cast(androidxViewModel);
    }

    @SuppressWarnings("unchecked")
    private <VM extends ViewModel> Class<VM> checkAndCastClass(Class<?> candidateClass) {
        if (!(ViewModel.class.isAssignableFrom(candidateClass))) {
            throw new IllegalArgumentException("Passed view-model class actually is not a ViewModel class");
        }
        if (!(androidx.lifecycle.ViewModel.class.isAssignableFrom(candidateClass))) {
            throw new IllegalArgumentException("Only androidx.lifecycle.ViewModel based view-models are supported by this factory");
        }
        // Safe cast, we already checked, that passed class extends both androidx view-model and our view-model
        return (Class<VM>) candidateClass;
    }

    @NonNull
    private <VM extends androidx.lifecycle.ViewModel> VM getAndroidXViewModel(@NonNull ViewModelStoreOwner storeOwner, @NonNull Class<VM> viewModelClass) {
        return new androidx.lifecycle.ViewModelProvider(storeOwner, this).get(viewModelClass);
    }

    @NonNull
    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> androidXModelClass) {
        Class<ViewModel<?>> frameworkViewModelClass = checkAndCastClass(androidXModelClass);
        ViewModel<?> viewModel = instanceProvider.create(frameworkViewModelClass);
        if (viewModel == null) {
            return super.create(androidXModelClass);
        }
        return androidXModelClass.cast(viewModel);
    }
}
