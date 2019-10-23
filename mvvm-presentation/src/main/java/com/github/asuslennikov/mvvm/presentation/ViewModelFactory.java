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

import androidx.annotation.Nullable;

import com.github.asuslennikov.mvvm.api.presentation.ViewModel;

/**
 * Фабрика по созданию и инициализации новых экземпляров {@link ViewModel}
 */
public interface ViewModelFactory {
    /**
     * Создание инстанса viewModel по его классу и его инициализация. Если фабрика не может создать экземпляр для переданного класса, то возвращается {@code null}.
     *
     * @param viewModelClass
     * @return проинициализированный экземпляр объекта viewModel, либо {@code null}
     */
    @Nullable
    <VM extends ViewModel<?>> VM create(Class<VM> viewModelClass);
}
