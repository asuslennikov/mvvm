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
package com.github.asuslennikov.mvvm.domain;


import androidx.annotation.NonNull;

import com.github.asuslennikov.mvvm.api.domain.UseCase;
import com.github.asuslennikov.mvvm.api.domain.UseCaseInput;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * It is base implementation of {@link UseCase} with reactive streams and LCE (Loading/Content/Error)
 * pattern, applied for output.
 *
 * @param <IN>  use case input information
 * @param <OUT> result of use case's work
 */
public abstract class AbstractUseCase<IN extends UseCaseInput,
        OUT extends AbstractUseCaseOutput> implements UseCase<IN, OUT> {

    /**
     * Method returns an object with {@link Scheduler} type. It defines which thread will be used
     * for actual work when use case is started.
     *
     * @return object which directs work to specific threads
     */
    @NonNull
    protected Scheduler getUseCaseScheduler() {
        return Schedulers.io();
    }

    @Override
    public Observable<OUT> execute(@NonNull IN useCaseInput) {
        UseCaseObservableOnSubscribe<IN, OUT> useCaseObservable = new UseCaseObservableOnSubscribe<>(useCaseInput, this);
        return Observable.create(useCaseObservable)
                .doOnDispose(useCaseObservable::onObservableDisposed)
                .doOnError(useCaseObservable::onObservableError)
                .subscribeOn(getUseCaseScheduler());
    }

    /**
     * It creates an instance for output results. Basically, successor should just call a constructor
     * for specific type. Do not perform any complicated logic here.
     * <p></p>
     * This method can be called more than once per execution.
     *
     * @return an instance for output results
     */
    @NonNull
    protected abstract OUT getUseCaseOutput();

    protected abstract void doExecute(@NonNull IN useCaseInput, @NonNull UseCaseExecution<OUT> execution);
}
