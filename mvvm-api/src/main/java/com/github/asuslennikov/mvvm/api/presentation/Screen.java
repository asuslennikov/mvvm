/*
 * Copyright 2019 Suslennikov Anton
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.asuslennikov.mvvm.api.presentation;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * It describes contract for communication with screen (in terms of Android framework it will be
 * Activity, Fragment or View). The main responsibilities are: rendering a state
 * ({@link #render(State)} and applying effects ({@link #applyEffect(Effect)}).
 * <p></p>
 * Implementation of this interface:
 * <ul>
 * <li>does all rendering tasks</li>
 * <li>is responsible for resources loading, which are needed for rendering (for example:
 * strings, images and so on) </li>
 * <li>never changes a {@link State} (it is responsibility of {@link ViewModel})</li>
 * <li>doesn't have any processing logic. It should delegate processing to {@link ViewModel}</li>
 * </ul>
 *
 * @param <STATE> Type which holds information for rendering of this screen (see {@link State})
 */
public interface Screen<STATE extends State> {

    /**
     * It provides a saved screen state. For example, we need it when activity was killed by
     * framework (due to low memory). Usually it is used for initialization of corresponding
     * {@link ViewModel}
     *
     * @return a current screen's state, can be {@code null}
     */
    @Nullable
    STATE getSavedState();

    /**
     * It performs all render actions for specific state. It calculates difference with the
     * previous state and applies changes to the screen (sets text, switches images and so on).
     * This method should be always called from the {@code main} thread, because there can be
     * operations with view hierarchy ({@link android.view.View}) during this method's execution.
     *
     * @param screenState a screen's state which should be rendered
     */
    void render(@NonNull STATE screenState);

    /**
     * This method applies a specific effect ({@link Effect}) to the screen. If screen doesn't
     * know about specific effect, then it should just ignore it.
     *
     * @param screenEffect a screen's effect which should be applied to the screen
     */
    void applyEffect(@NonNull Effect screenEffect);

}
