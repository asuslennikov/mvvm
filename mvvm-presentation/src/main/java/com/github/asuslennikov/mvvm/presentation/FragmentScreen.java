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

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStoreOwner;

import com.github.asuslennikov.mvvm.api.presentation.Effect;
import com.github.asuslennikov.mvvm.api.presentation.Screen;
import com.github.asuslennikov.mvvm.api.presentation.State;
import com.github.asuslennikov.mvvm.api.presentation.ViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * An implementation of {@link Screen} interface, which is linked with specific {@link ViewModel}
 * and based on activity (see {@link Fragment}).
 *
 * @param <STATE> Type of class, which contains render information for this screen (see {@link State})
 * @param <VM>    Type of class, which contains screen behaviour (see {@link ViewModel})
 */
public abstract class FragmentScreen<STATE extends State, VM extends ViewModel<STATE>>
        extends Fragment implements Screen<STATE> {

    private static final long UI_THROTTLE_INTERVAL = 100L;

    private VM viewModel;
    private CompositeDisposable disposable;
    private STATE state;

    /**
     * This method is responsible for correct instantiation of view model (with respect of all
     * dependencies). Usually it just calls {@link ViewModelProvider#getViewModel(ViewModelStoreOwner, Class)},
     * where {@code ViewModelStoreOwner} is this fragment.
     *
     * @return an initialized view model instance
     */
    @NonNull
    protected abstract VM createViewModel();

    /**
     * It provides access to view model. If it is not initialized yet, the {@link #createViewModel()}
     * will be called, otherwise it will return the saved instance.
     *
     * @return an initialized view model instance
     */
    @NonNull
    protected VM getViewModel() {
        if (viewModel == null) {
            viewModel = createViewModel();
        }
        return viewModel;
    }

    /**
     * Method returns an object with {@link Scheduler} type. It defines which thread will be used
     * for {@link #render(State)} or {@link #applyEffect(Effect) } execution. This method can be
     * called several times.
     *
     * @return object which directs work to specific threads
     */
    @NonNull
    protected Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

    /**
     * It defines a throttling interval in milliseconds for rendering event (it helps to avoid an
     * unnecessary reaction to UI event which was already replaced by another one). More detailed
     * explanation can be found in documentation for
     * {@link Observable#throttleLatest(long, TimeUnit, boolean)}.
     *
     * @return filtration interval in milliseconds
     */
    protected long getUiThrottleIntervalInMillis() {
        return UI_THROTTLE_INTERVAL;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disposable = new CompositeDisposable();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel == null) {
            throw new IllegalStateException("View model for this screen is not initialized");
        }
        disposable.add(viewModel.getState(this)
                .throttleLatest(getUiThrottleIntervalInMillis(), TimeUnit.MILLISECONDS, true)
                .observeOn(getScheduler())
                .subscribe(this::render));
        disposable.add(viewModel.getEffect(this)
                .throttleLatest(getUiThrottleIntervalInMillis(), TimeUnit.MILLISECONDS, true)
                .observeOn(getScheduler())
                .subscribe(this::applyEffect));
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.clear();
    }

    @Nullable
    @Override
    public STATE getSavedState() {
        return state;
    }

    @Override
    @CallSuper
    public void render(@NonNull STATE screenState) {
        this.state = screenState;
    }
}
