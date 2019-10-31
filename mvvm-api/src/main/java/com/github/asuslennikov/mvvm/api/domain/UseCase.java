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
package com.github.asuslennikov.mvvm.api.domain;


import androidx.annotation.NonNull;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;

/**
 * It is general contract which allow any caller to execute a specific business scenario with
 * given input and receive an output. By term 'business scenario' we assume some set of actions
 * (or predefined rules, or data transformation steps) which are performed on given input. When
 * use case completes (or fails to complete) these actions, it can provide some information in
 * response or just notify about the end of work.
 * <p></p>
 * Example: CreateRepositoryUseCase - it takes some info (repository name, maybe access rights),
 * performs necessary actions (connects to the server, checks correctness, executes creation steps)
 * and as a result returns repository address in case of success, or reason of fail.
 *
 * @param <IN>  use case input information
 * @param <OUT> result of use case's work
 */
public interface UseCase<IN extends UseCaseInput, OUT extends UseCaseOutput> {
    /**
     * This method triggers use case execution. An actual work thread should be chosen by
     * implementation itself (implementation should call {@link Observable#subscribeOn(Scheduler)}).
     * Use case implementation shouldn't care about back-pressure, it's  caller's responsibility
     * to convert {@link Observable} to {@link Flowable} (for example with
     * ({@link Observable#toFlowable(BackpressureStrategy)}).
     * <p></p>
     * In most implementations an actual work will be started only when caller subscribes to
     * observable (return value), see {@link Observable#subscribe(Consumer, Consumer)}.
     *
     * @param useCaseInput input information for use case
     * @return object for registering a subscriber for work's result (and also intermediate
     * notifications, if the implementation provides any)
     */
    Observable<OUT> execute(@NonNull IN useCaseInput);
}
