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
package com.github.asuslennikov.mvvm.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStoreOwner;

import com.github.asuslennikov.mvvm.api.presentation.Screen;
import com.github.asuslennikov.mvvm.api.presentation.State;
import com.github.asuslennikov.mvvm.api.presentation.ViewModel;

/**
 * It is contract for creating {@link ViewModel} instances with regard of lifecycle for
 * specific {@link Screen}. In most cases, implementation will delegate calls to AndroidX
 * {@link androidx.lifecycle.ViewModelProvider}. It's quite recommended to extract an actual
 * creation logic into {@link ViewModelFactory} and keep only lifecycle logic in implementation.
 */
@FunctionalInterface
public interface ViewModelProvider {

    /**
     * This method forge a view model with all its dependencies and with regard of lifecycle.
     *
     * @param storeOwner     owner, which lifecycle will be used to handle view models properly.
     * @param viewModelClass a class of view model
     * @param <STATE>        screen's state type
     * @param <VM>           exact view model type, which we want to instantiate
     * @return an instance of specified view model type
     */
    @NonNull
    <STATE extends State, VM extends ViewModel<STATE>> VM getViewModel(@NonNull ViewModelStoreOwner storeOwner, @NonNull Class<VM> viewModelClass);

    /**
     * If you want to create several view models, with the same lifecycle (for example, for recycler
     * view) you can get a wrapper, which holds a lifecycle owner
     *
     * @param storeOwner owner, which lifecycle will be used to handle view models properly.
     * @return a wrapper with specified owner which will delegate creation to
     * {@link #getViewModel(ViewModelStoreOwner, Class)}
     */
    default Linked linkWithStore(@NonNull ViewModelStoreOwner storeOwner) {
        return new Linked() {
            @Override
            public <STATE extends State, VM extends ViewModel<STATE>> VM getViewModel(@NonNull Class<VM> viewModelClass) {
                return ViewModelProvider.this.getViewModel(storeOwner, viewModelClass);
            }
        };
    }

    /**
     * A wrapper, which holds reference to a specific lifecycle owner and delegates call to
     * {@link #getViewModel(ViewModelStoreOwner, Class)}.
     */
    @FunctionalInterface
    interface Linked {
        /**
         * This method forge a view model with all its dependencies and with regard of lifecycle.
         *
         * @param viewModelClass a class of view model
         * @param <STATE>        screen's state type
         * @param <VM>           exact view model type, which we want to instantiate
         * @return an instance of specified view model type
         */
        <STATE extends State, VM extends ViewModel<STATE>> VM getViewModel(@NonNull Class<VM> viewModelClass);
    }
}
