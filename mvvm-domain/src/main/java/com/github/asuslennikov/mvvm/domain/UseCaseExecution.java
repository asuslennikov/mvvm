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

import androidx.annotation.NonNull;

import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Class defines contract for communication between current use case execution and subscribers.
 *
 * @param <OUT> result of use case's work (successor of {@link AbstractUseCaseOutput})
 */
public final class UseCaseExecution<OUT extends AbstractUseCaseOutput> {
    private final AbstractUseCase<?, OUT> useCase;
    private final ObservableEmitter<OUT> emitter;
    private Map<String, Disposable> disposables;

    UseCaseExecution(AbstractUseCase<?, OUT> useCase, ObservableEmitter<OUT> emitter) {
        this.useCase = useCase;
        this.emitter = emitter;
    }

    private OUT getUseCaseOutput() {
        return useCase.getUseCaseOutput();
    }

    /**
     * Method sends notification for subscribers.
     *
     * @param useCaseOutput current state of work
     */
    private void notify(@NonNull OUT useCaseOutput) {
        if (!isCancelled()) {
            emitter.onNext(useCaseOutput);
        }
    }

    /**
     * Method sends notification for subscribers about start of execution.
     * (In fact it set the {@link UseCaseOutput.Status#IN_PROGRESS} status for output, received
     * via {@link AbstractUseCase#getUseCaseOutput()}).
     */
    public void notifyProgress() {
        OUT useCaseOutput = getUseCaseOutput();
        notifyProgress(useCaseOutput);
    }

    /**
     * Method sends notification for subscribers about start of execution.
     * (In fact it set the {@link UseCaseOutput.Status#IN_PROGRESS} status for output, received
     * as argument).
     *
     * @param useCaseOutput pre-filled output instance
     */
    public void notifyProgress(@NonNull OUT useCaseOutput) {
        useCaseOutput.setStatus(UseCaseOutput.Status.IN_PROGRESS);
        notify(useCaseOutput);
    }

    /**
     * Method sends notification for subscribers about completion of execution.
     * (In fact it set the {@link UseCaseOutput.Status#SUCCESS} status for output, received
     * via {@link AbstractUseCase#getUseCaseOutput()}).
     */
    public void notifySuccess() {
        OUT useCaseOutput = getUseCaseOutput();
        notifySuccess(useCaseOutput);
    }

    /**
     * Method sends notification for subscribers about completion of execution.
     * (In fact it set the {@link UseCaseOutput.Status#SUCCESS} status for output, received
     * as argument).
     *
     * @param useCaseOutput pre-filled output instance
     */
    public void notifySuccess(@NonNull OUT useCaseOutput) {
        useCaseOutput.setStatus(UseCaseOutput.Status.SUCCESS);
        notify(useCaseOutput);
    }

    /**
     * Method sends notification for subscribers about failure of execution.
     * (In fact it set the {@link UseCaseOutput.Status#FAILURE} status for output, received
     * via {@link AbstractUseCase#getUseCaseOutput()}).
     */
    public void notifyFailure() {
        OUT useCaseOutput = getUseCaseOutput();
        useCaseOutput.setStatus(UseCaseOutput.Status.FAILURE);
        notify(useCaseOutput);
    }

    /**
     * Method sends notification for subscribers about failure of execution.
     * (In fact it set the {@link UseCaseOutput.Status#FAILURE} status for output, received
     * as argument).
     *
     * @param useCaseOutput pre-filled output instance
     */
    public void notifyFailure(@NonNull OUT useCaseOutput) {
        useCaseOutput.setStatus(UseCaseOutput.Status.FAILURE);
        notify(useCaseOutput);
    }

    /**
     * Method sends notification for subscribers about failure of execution.
     * (In fact it set the {@link UseCaseOutput.Status#FAILURE} status for output, received
     * via {@link AbstractUseCase#getUseCaseOutput()} and populate it with the given exception).
     *
     * @param ex exception, which was thrown during execution
     */
    public void notifyFailure(@NonNull Throwable ex) {
        OUT useCaseOutput = getUseCaseOutput();
        useCaseOutput.setStatus(UseCaseOutput.Status.FAILURE);
        useCaseOutput.setException(ex);
        notify(useCaseOutput);
    }

    /**
     * Method allows to check the current execution cancellation status. Can be helpful if your
     * use case does a lot of work and you don't want to do unnecessary work if there is no
     * subscribers.
     *
     * @return {@code true} if there is no subscribers for current execution
     */
    public boolean isCancelled() {
        return emitter.isDisposed();
    }

    private boolean hasJoinedTasks() {
        if (disposables != null && !disposables.isEmpty()) {
            for (Disposable disposable : disposables.values()) {
                if (!disposable.isDisposed()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Terminates all child tasks for current execution.
     */
    void terminateJoinedTasks() {
        if (disposables != null && !disposables.isEmpty()) {
            for (Disposable disposable : disposables.values()) {
                disposable.dispose();
            }
        }
    }

    /**
     * Method postpones completion of current execution until the
     * {@link #completeExecution(boolean)} is called.
     *
     * @param uniqueTaskName a unique task id. If this execution already has active task with given
     *                       id, the {@link IllegalArgumentException} will be thrown
     * @param disposable     result of subscription to child task
     * @return the same {@code disposable}, which was passed as argument
     */
    @NonNull
    public Disposable joinTask(@NonNull String uniqueTaskName, @NonNull Disposable disposable) {
        if (disposables == null) {
            disposables = new ConcurrentHashMap<>();
        } else {
            Disposable oldTask = disposables.get(uniqueTaskName);
            if (oldTask != null && !oldTask.isDisposed()) {
                throw new IllegalArgumentException("Task name must be unique for concrete execution.");
            }
        }
        disposables.put(uniqueTaskName, disposable);
        return disposable;
    }

    /**
     * Method terminates a child task, associated with given id.
     *
     * @param uniqueTaskName a unique task id.
     * @return {@code true} if there was a running task with specified id.
     */
    public boolean cancelTask(@NonNull String uniqueTaskName) {
        Disposable disposable = disposables.get(uniqueTaskName);
        if (disposable == null || disposable.isDisposed()) {
            return false;
        }
        disposable.dispose();
        // Может оказаться, что это была последняя связанная заадча, токда нужно завершить выполнение сценария
        completeExecution(false);
        return true;
    }

    /**
     * Method provides an error handler for {@link Observable}, which, if triggered, terminates
     * the current execution (and all child tasks {@link #joinTask(String, Disposable)}).
     * Subscribers will be automatically notified about failure.
     *
     * @return error handler
     */
    public Consumer<Throwable> notifyFailureOnError() {
        return throwable -> {
            notifyFailure(throwable);
            completeExecution(true);
        };
    }

    /**
     * Method terminates the current execution, if there is no child tasks or
     * {@code terminateJoinedTasks} is true. After that, no notification will be delivered
     * to subscribers.
     *
     * @param terminateJoinedTasks {@code true} if we want complete execution, even if there
     *                             is active child tasks
     */
    public void completeExecution(boolean terminateJoinedTasks) {
        if (!isCancelled() && (!hasJoinedTasks() || terminateJoinedTasks)) {
            emitter.onComplete();
        }
        if (terminateJoinedTasks) {
            terminateJoinedTasks();
        }
    }
}
