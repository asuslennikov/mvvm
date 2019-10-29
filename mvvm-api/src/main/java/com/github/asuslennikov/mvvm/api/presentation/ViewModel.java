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
package com.github.asuslennikov.mvvm.api.presentation;

import androidx.annotation.NonNull;

import io.reactivex.Observable;

/**
 * This class describes contract for screen's handler. It's responsible for processing UI inputs,
 * such as clicks, text changes. It also controls screen's state and communicates with domain
 * layer.
 *
 * @param <STATE> Type which holds information for screen's state (see {@link State})
 */
public interface ViewModel<STATE extends State> {

    /**
     * It allows to subscribe to screen's state updates.
     *
     * @param screen target object, for which a caller want to receive state's updates.
     *               Implementation decides how to process that object, but most common
     *               usage is to call the {@link Screen#getSavedState()}
     * @return RxJava observable
     */
    @NonNull
    Observable<STATE> getState(@NonNull Screen<STATE> screen);

    /**
     * It allows to subscribe to screen's effects.
     *
     * @param screen target object, for which a caller want to receive state's updates.
     *               Implementation decides how to process that object, but most common
     *               usage is to call the {@link Screen#getSavedState()}
     * @return RxJava observable
     */
    @NonNull
    Observable<Effect> getEffect(@NonNull Screen<STATE> screen);
}
