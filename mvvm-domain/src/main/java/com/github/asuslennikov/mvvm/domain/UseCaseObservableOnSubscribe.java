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
package com.github.asuslennikov.mvvm.domain;

import com.github.asuslennikov.mvvm.api.domain.UseCaseInput;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

final class UseCaseObservableOnSubscribe<IN extends UseCaseInput,
        OUT extends AbstractUseCaseOutput> implements ObservableOnSubscribe<OUT> {

    private final IN useCaseInput;
    private final AbstractUseCase<IN, OUT> useCase;
    private UseCaseExecution<IN, OUT> execution;

    UseCaseObservableOnSubscribe(IN useCaseInput, AbstractUseCase<IN, OUT> useCase) {
        this.useCaseInput = useCaseInput;
        this.useCase = useCase;
    }

    @Override
    public void subscribe(ObservableEmitter<OUT> emitter) throws Exception {
        execution = new UseCaseExecution<>(useCase, useCaseInput, emitter);
        try {
            useCase.doExecute(useCaseInput, execution);
        } catch (Throwable t) {
            execution.notifyFailure(t);
        }
        execution.completeExecution(false);
    }

    /**
     * We need to terminate all child tasks (if any) for current execution, if it's cancelled.
     */
    void onObservableDisposed() {
        if (execution != null) {
            execution.terminateJoinedTasks();
        }
    }

    /**
     * We need to terminate all child tasks (if any) for current execution, if it's cancelled.
     */
    void onObservableError(Throwable ignored) {
        if (execution != null) {
            execution.terminateJoinedTasks();
        }
    }
}
