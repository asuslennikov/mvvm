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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.asuslennikov.mvvm.api.presentation.Effect;
import com.github.asuslennikov.mvvm.api.presentation.Screen;
import com.github.asuslennikov.mvvm.api.presentation.State;
import com.github.asuslennikov.mvvm.api.presentation.ViewModel;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * This is base class, which incapsulates logic for publishing screen states and effects.
 *
 * @param <STATE> Type which holds information for rendering of screen (see {@link State})
 */
public abstract class AbstractViewModel<STATE extends State> extends androidx.lifecycle.ViewModel implements ViewModel<STATE> {
    private final BehaviorSubject<STATE> statePublisher;
    private PublishSubject<Effect> effectPublisher;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private STATE currentState;

    public AbstractViewModel() {
        statePublisher = BehaviorSubject.create();
    }

    /**
     * Method provides an initial value for screen state ({@link State}).
     *
     * @return an instance of state which represents initial state of screen
     */
    @NonNull
    protected abstract STATE buildInitialState();

    /**
     * Method gives access to the current screen state.
     *
     * @return the current screen state
     */
    @NonNull
    protected final STATE getCurrentState() {
        if (currentState == null) {
            currentState = buildInitialState();
        }
        return currentState;
    }

    @NonNull
    @Override
    public Observable<STATE> getState(@NonNull Screen<STATE> screen) {
        STATE state = mergeState(currentState, screen.getSavedState());
        sendState(state);
        return statePublisher;
    }

    /**
     * Method merges the current state and another state. By default it returns {@code currentState}
     * if it isn't {@code null}. If it is {@code null}, then it checks the {@code savedState} and
     * if it isn't {@code null}, returns it or creates an initial state otherwise
     * ({@link #buildInitialState()})
     *
     * @param currentState current value (value from this {@code ViewModel})
     * @param savedState   external value (for example from screen)
     * @return result of merge
     */
    @NonNull
    protected STATE mergeState(@Nullable STATE currentState, @Nullable STATE savedState) {
        if (currentState == null && savedState != null) {
            return savedState;
        }
        if (currentState == null) {
            currentState = buildInitialState();
        }
        return currentState;
    }

    /**
     * Method sends the passed state to subscribers (for example {@link Screen}). It also
     * saves this states, so it can be accessed via {@link #getCurrentState()}.
     *
     * @param state new state of screen
     */
    protected final void sendState(@Nullable STATE state) {
        if (state != null) {
            currentState = state;
            statePublisher.onNext(currentState);
        }
    }

    @NonNull
    @Override
    public Observable<Effect> getEffect(@NonNull Screen<STATE> ignoredScreen) {
        if (effectPublisher == null) {
            effectPublisher = PublishSubject.create();
        }
        return effectPublisher;
    }

    /**
     * Method notifies subscribers that the passed effect should be applied.
     *
     * @param effect an effect to be applied to a screen
     */
    protected final void sendEffect(@Nullable Effect effect) {
        if (effect != null && effectPublisher != null) {
            effectPublisher.onNext(effect);
        }
    }

    /**
     * Method allows automatically cancel a disposable when this view model finishes.
     *
     * @param disposable result of subscription
     * @return the same disposable
     */
    protected Disposable collectDisposable(Disposable disposable) {
        this.disposable.add(disposable);
        return disposable;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        statePublisher.onComplete();
        if (effectPublisher != null) {
            effectPublisher.onComplete();
        }
        disposable.clear();
    }
}
