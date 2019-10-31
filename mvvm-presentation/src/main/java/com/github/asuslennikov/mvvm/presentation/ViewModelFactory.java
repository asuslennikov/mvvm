/*
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

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.github.asuslennikov.mvvm.api.presentation.ViewModel;

/**
 * It is contract for creating {@link ViewModel} instances. In fact it's mirroring the
 * {@link ViewModelProvider.Factory} interface with change in return type. Implementation
 * shouldn't care about android lifecycle, its main responsibility is to create a new instance.
 */
public interface ViewModelFactory {
    /**
     * This method creates a new instance of specified class. It takes in account all class dependency.
     * If this factory can't create an instance of specific type (for example, if it doesn't have
     * enough dependencies) then it returns {@code null} as a result.
     *
     * @param viewModelClass concrete view model type
     * @return an initialized instance of view model or {@code null}
     */
    @Nullable
    <VM extends ViewModel<?>> VM create(Class<VM> viewModelClass);
}
