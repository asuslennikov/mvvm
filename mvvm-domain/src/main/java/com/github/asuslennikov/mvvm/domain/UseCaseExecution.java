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

import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Класс осуществляющий взаимодействие между подписчиком и конкретным запуском сценария.
 *
 * @param <OUT> тип результата работы (наследник {@link AbstractUseCaseOutput})
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
     * Метод оповещает слушателей о результате работы сценария в том виде, как он был передан
     *
     * @param useCaseOutput результат работы
     */
    private void notify(@NonNull OUT useCaseOutput) {
        if (!isCancelled()) {
            emitter.onNext(useCaseOutput);
        }
    }

    /**
     * Метод оповещает слушателей о выполнении сценария
     * (устанавливает статус {@link UseCaseOutput.Status#IN_PROGRESS}).
     */
    public void notifyProgress() {
        OUT useCaseOutput = getUseCaseOutput();
        notifyProgress(useCaseOutput);
    }

    /**
     * Метод оповещает слушателей о выполнении сценария
     * (устанавливает статус {@link UseCaseOutput.Status#IN_PROGRESS}).
     *
     * @param useCaseOutput предзаполненный результат выполнения сценария
     */
    public void notifyProgress(@NonNull OUT useCaseOutput) {
        useCaseOutput.setStatus(UseCaseOutput.Status.IN_PROGRESS);
        notify(useCaseOutput);
    }

    /**
     * Метод оповещает слушателей об успешном завершении работы сценария
     * (устанавливает статус {@link UseCaseOutput.Status#SUCCESS}).
     */
    public void notifySuccess() {
        OUT useCaseOutput = getUseCaseOutput();
        notifySuccess(useCaseOutput);
    }

    /**
     * Метод оповещает слушателей об успешном завершении работы сценария
     * (устанавливает статус {@link UseCaseOutput.Status#SUCCESS}).
     *
     * @param useCaseOutput предзаполненный результат выполнения сценария
     */
    public void notifySuccess(@NonNull OUT useCaseOutput) {
        useCaseOutput.setStatus(UseCaseOutput.Status.SUCCESS);
        notify(useCaseOutput);
    }

    /**
     * Метод оповещает слушателей об ошибочном завершении работы сценария
     * (устанавливает статус {@link UseCaseOutput.Status#FAILURE}).
     */
    public void notifyFailure() {
        OUT useCaseOutput = getUseCaseOutput();
        useCaseOutput.setStatus(UseCaseOutput.Status.FAILURE);
        notify(useCaseOutput);
    }

    /**
     * Метод оповещает слушателей об ошибочном завершении работы сценария
     * (устанавливает статус {@link UseCaseOutput.Status#FAILURE}).
     *
     * @param useCaseOutput предзаполненный результат выполнения сценария
     */
    public void notifyFailure(@NonNull OUT useCaseOutput) {
        useCaseOutput.setStatus(UseCaseOutput.Status.FAILURE);
        notify(useCaseOutput);
    }

    /**
     * Метод оповещает слушателей об ошибочном завершении работы сценария
     * (устанавливает статус {@link UseCaseOutput.Status#FAILURE})
     * и указывает на конкретную ошибку.
     *
     * @param ex произошедшая ошибка
     */
    public void notifyFailure(@NonNull Throwable ex) {
        OUT useCaseOutput = getUseCaseOutput();
        useCaseOutput.setStatus(UseCaseOutput.Status.FAILURE);
        useCaseOutput.setException(ex);
        notify(useCaseOutput);
    }

    /**
     * Метод позволяет узнать, необходимо ли выполнять задачу. Может применяться для затратных по
     * времени операций, когда получатель результата уже может в нем не нуждаться.
     *
     * @return {@code true} если результат работы не будет никем получен
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
     * Завершает все связанные задачи (если есть) для текущего выполнения сценария.
     */
    void terminateJoinedTasks() {
        if (disposables != null && !disposables.isEmpty()) {
            for (Disposable disposable : disposables.values()) {
                disposable.dispose();
            }
        }
    }

    /**
     * Метод позволяет текущему запуску сценария дождаться завершения указанной задачи.
     *
     * @param uniqueTaskName уникальный идентификатор задачи. В случае, если для данного выполнения сценария
     *                       уже зарегистрирована задача с таким идентификатором, будет выбрашено исключени
     *                       {@link IllegalArgumentException}.
     * @param disposable     внутренняя задача, завершения которой необходимо дождаться данному запуску сценария
     * @return переданная задача, на случай, если необходимо сохранить ссылку внутри сценария
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
     * Метод заверщает выполнение связанной задачи.
     *
     * @param uniqueTaskName уникальный идентификатор задачи.
     * @return {@code true} если связанная задача с таким идентификатором была найде выполняющейся и остановлена.
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
     * Метод возвращает обработчик ошибок для {@link Observable}, который в случае срабатывания отправляет подписчику
     * текущего выполнения сценария уведомление об ошибке. После этого, выполнение сценария и всех зависимых задач
     * ({@link #joinTask(String, Disposable)}) завершается.
     *
     * @return обработчик ошибок в потоке ({@link Observable})
     */
    public Consumer<Throwable> notifyFailureOnError() {
        return throwable -> {
            notifyFailure(throwable);
            completeExecution(true);
        };
    }

    /**
     * Метод позволяет в подать сигнал о завершении работы текущего запуска сценария. После этого подписчику
     * выполнения данного сценария никакие сообщения не будут доставлены.
     *
     * @param terminateJoinedTasks определяет нужно ли завершить ожидаемые задачи
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
