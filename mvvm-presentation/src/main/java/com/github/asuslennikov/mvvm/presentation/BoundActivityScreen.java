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

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.github.asuslennikov.mvvm.api.presentation.Effect;
import com.github.asuslennikov.mvvm.api.presentation.Screen;
import com.github.asuslennikov.mvvm.api.presentation.State;
import com.github.asuslennikov.mvvm.api.presentation.ViewModel;

/**
 * An implementation of {@link Screen} interface, which is linked with specific {@link ViewModel}
 * and based on activity (see {@link AppCompatActivity}). The rendering process is performed
 * by <a href="https://developer.android.com/topic/libraries/data-binding">data-binding</a>
 * technology.
 *
 * @param <STATE> Type of class, which contains render information for this screen (see {@link State})
 * @param <VM>    Type of class, which contains screen behaviour (see {@link ViewModel})
 * @param <B>     Type of data-binding class
 */
public abstract class BoundActivityScreen<STATE extends State, VM extends ViewModel<STATE>,
        B extends ViewDataBinding> extends ActivityScreen<STATE, VM> {

    private static final int NO_ACTUAL_ID = 0;
    private B binding;

    /**
     * It provides a layout id with data-binding support for this screen.
     *
     * @return a layout id
     */
    @LayoutRes
    protected abstract int getLayoutResourceId();

    /**
     * It provides a data-binding id for state variable (check the {@code variable} value in
     * {@code data} section of layout file, provided by {@link #getLayoutResourceId()}). It will be
     * used for {@link ViewDataBinding#setVariable(int, Object)}.
     *
     * @return an id for state variable
     * @see #render(State)
     */
    protected abstract int getBindingStateVariableId();

    /**
     * It provides a data-binding id for view model variable (check the {@code variable} value in
     * {@code data} section of layout file, provided by {@link #getLayoutResourceId()}). It will be
     * used for {@link ViewDataBinding#setVariable(int, Object)}.
     *
     * @return an id for view model variable
     */
    protected abstract int getBindingViewModelVariableId();

    /**
     * It provides a data-binding id for screen variable (check the {@code variable} value in
     * {@code data} section of layout file, provided by {@link #getLayoutResourceId()}). It will be
     * used for {@link ViewDataBinding#setVariable(int, Object)}. This variable is not mandatory
     * and can be skipped in your layout.
     *
     * @return an id for screen variable
     */
    protected int getBindingScreenVariableId() {
        return NO_ACTUAL_ID;
    }

    /**
     * Method provides access for data-binding instance of this screen (so you can easily use views
     * with id).
     *
     * @return a data-binding instance
     */
    @NonNull
    protected B getBinding() {
        if (binding == null) {
            throw new IllegalStateException("You called the #getBinding() before #onCreate(Bundle)");
        }
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId());
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
