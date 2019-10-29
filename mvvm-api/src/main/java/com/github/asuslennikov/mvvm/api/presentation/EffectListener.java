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

/**
 * It is describes contract for effect's execution listener. At least, implementation should receive
 * notifications about start and stop of effects's execution (see {@link Effect}.
 * <p></p>
 * <b>Not for all effects we can determine the beginning and completion, so you should consider this
 * API as unstable.</b>
 */
public interface EffectListener {
    /**
     * This method called at the beginning of effect's execution.
     *
     * @param effect source of notification
     */
    void effectStarted(Effect effect);

    /**
     * This method called right after completion of effect.
     *
     * @param effect source of notification
     */
    void effectFinished(Effect effect);
}
