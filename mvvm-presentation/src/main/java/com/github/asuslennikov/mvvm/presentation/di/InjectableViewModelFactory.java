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
