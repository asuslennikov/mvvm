/**
 * Copyright 2019 Suslennikov Anton
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.asuslennikov.mvvm.presentation.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStoreOwner;

import com.github.asuslennikov.mvvm.api.presentation.State;
import com.github.asuslennikov.mvvm.api.presentation.ViewModel;
import com.github.asuslennikov.mvvm.presentation.ViewModelFactory;
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider;

import javax.inject.Inject;

/**
 * This class is an implementation of the Factory pattern for {@link ViewModel}. It also a bridge
 * between AndroidX view models and view models from this library. As a result, we have all advantages
 * of AndroidX view models out-of-box.
 *
 * @see androidx.lifecycle.ViewModelProvider.NewInstanceFactory
 */
public final class AndroidXViewModelProvider extends androidx.lifecycle.ViewModelProvider.NewInstanceFactory implements ViewModelProvider {
    private final ViewModelFactory instanceFactory;

    @Inject
    public AndroidXViewModelProvider(ViewModelFactory instanceFactory) {
        this.instanceFactory = instanceFactory;
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
        ViewModel<?> viewModel = instanceFactory.create(frameworkViewModelClass);
        if (viewModel == null) {
            return super.create(androidXModelClass);
        }
        return androidXModelClass.cast(viewModel);
    }
}
