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

import com.github.asuslennikov.mvvm.api.presentation.Effect;
import com.github.asuslennikov.mvvm.api.presentation.EffectListener;

/**
 * It is a helper class, which provides ability to set only start (or only end) effect listener.
 * <pre>
 * public void editButtonClicked() {
 *     Effect openKeyboardEffect = new OpenKeyboardEffect();
 *     openKeyboardEffect.setListener(new LambdaEffectListener()
 *         .setFinishEffectListener(effect -> doSomething())
 *     screenEffectPublisher.onNext(openKeyboardEffect);
 * }
 * </pre>
 */
public final class LambdaEffectListener implements EffectListener {

    private EffectEventListener startEffectListener;
    private EffectEventListener finishEffectListener;

    @Override
    public void effectStarted(Effect effect) {
        if (startEffectListener != null) {
            startEffectListener.onEvent(effect);
        }
    }

    @Override
    public void effectFinished(Effect effect) {
        if (finishEffectListener != null) {
            finishEffectListener.onEvent(effect);
        }
    }

    /**
     * It is replacement for standard {@link java.util.function.Consumer} for backward compatibility
     * with old Android versions.
     */
    @FunctionalInterface
    public interface EffectEventListener {
        void onEvent(Effect effect);
    }

    /**
     * It is 'chained' setter, which allows to specify receiver for notification about effect start.
     *
     * @param startEffectListener notification receiver
     * @return this instance
     */
    public LambdaEffectListener setStartEffectListener(EffectEventListener startEffectListener) {
        this.startEffectListener = startEffectListener;
        return this;
    }

    /**
     * It is 'chained' setter, which allows to specify receiver for notification about effect end.
     *
     * @param finishEffectListener notification receiver
     * @return this instance
     */
    public LambdaEffectListener setFinishEffectListener(EffectEventListener finishEffectListener) {
        this.finishEffectListener = finishEffectListener;
        return this;
    }
}
