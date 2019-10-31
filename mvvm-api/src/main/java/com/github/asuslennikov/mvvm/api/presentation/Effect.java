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

import androidx.annotation.Nullable;

/**
 * It describes one-shot event (maybe prolonged in time) which should be displayed by a screen
 * ({@link Screen}). For example, it can be some animation, change of keyboard state or
 * something else. This event will be executed only once (so it will never performed again after
 * orientation change).
 */
public interface Effect {

    /**
     * This method sets a listener for effect's execution (its start / stop).
     * <p></p>
     * The default realization just ignores the passed value. Currently, most effects doesn't
     * provide any way to track its execution, so why bother with creating an empty method in each
     * implementation.
     *
     * @param listener a receiver for effect's notifications
     */
    default void setListener(@Nullable EffectListener listener) {
        // ignore the passed listener and do nothing
    }
}
