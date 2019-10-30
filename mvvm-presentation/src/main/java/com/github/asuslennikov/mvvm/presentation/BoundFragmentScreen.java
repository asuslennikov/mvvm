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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.github.asuslennikov.mvvm.api.presentation.Effect;
import com.github.asuslennikov.mvvm.api.presentation.Screen;
import com.github.asuslennikov.mvvm.api.presentation.State;
import com.github.asuslennikov.mvvm.api.presentation.ViewModel;

/**
 * Реализация {@link Screen}, связывающая его с конкретной {@link ViewModel}
 * и основанная на фрагментах ({@link Fragment}). Базовая отрисовка экрана
 * происходит с помощью технологии
 * <a href="https://developer.android.com/topic/libraries/data-binding">data-binding</a>.
 *
 * @param <STATE> Класс, описывающий состояние данного экрана (см. {@link State})
 * @param <VM>    Класс, описывающий поведение экрана (см. {@link ViewModel})
 * @param <B>     Класс биндинга
 */
public abstract class BoundFragmentScreen<STATE extends State, VM extends ViewModel<STATE>,
        B extends ViewDataBinding> extends FragmentScreen<STATE, VM> {

    private static final int NO_ACTUAL_ID = 0;
    private B binding;

    /**
     * Метод возвращает  идентификатор файла разметки.
     *
     * @return идентификатор файла разметки
     */
    protected abstract int getLayoutResourceId();

    /**
     * Возвращает идентификатор переменной биндинга для состояния экрана. Необходимо для корректной работы
     * метода {@link ViewDataBinding#setVariable(int, Object)}
     *
     * @return идентификатор переменной биндинга для состояния экрана
     * @see #render(State)
     */
    protected abstract int getBindingStateVariableId();

    /**
     * Возвращает идентификатор переменной биндинга для обработчика экрана. Необходимо для корректной работы
     * метода {@link ViewDataBinding#setVariable(int, Object)}
     *
     * @return идентификатор переменной биндинга для обработчика экрана
     */
    protected abstract int getBindingViewModelVariableId();

    /**
     * Возвращает идентификатор переменной биндинга для экрана. Необходимо для корректной работы
     * метода {@link ViewDataBinding#setVariable(int, Object)}. Может потребоваться когда одна {@link ViewModel}
     * обрабатывает несколько экранов и требуется передать текущий экран в качестве аргумента для обработчика UI события
     *
     * @return идентификатор переменной биндинга для экрана
     */
    protected int getBindingScreenVariableId() {
        return NO_ACTUAL_ID;
    }

    /**
     * Метод возвращает экземпляр data-binding'a для данного экрана.
     *
     * @return экземпляр data-binding'a
     */
    @NonNull
    protected B getBinding() {
        if (binding == null) {
            throw new IllegalStateException("You called the #getBinding() before #onCreateView(LayoutInflater, ViewGroup, Bundle)");
        }
        return binding;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int screenVariableId = getBindingScreenVariableId();
        if (NO_ACTUAL_ID != screenVariableId) {
            binding.setVariable(screenVariableId, this);
        }
        binding.setVariable(getBindingViewModelVariableId(), getViewModel());
    }

    @Override
    public void render(@NonNull STATE screenState) {
        super.render(screenState);
        binding.setVariable(getBindingStateVariableId(), screenState);
    }

    @Override
    public void applyEffect(@NonNull Effect screenEffect) {
        // no effects for this screen
    }
}
