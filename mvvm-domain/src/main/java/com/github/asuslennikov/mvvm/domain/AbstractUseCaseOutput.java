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
import androidx.annotation.Nullable;

import com.github.asuslennikov.mvvm.api.domain.UseCase;
import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput;

/**
 * Базовая реализация представления результатов работы {@link UseCase}, позволяющая оперировать статусом и запоминать возможные ошибки, произошедшие в сценарии.
 */
public abstract class AbstractUseCaseOutput implements UseCaseOutput {
    private Throwable exception;
    private Status status = Status.SUCCESS;

    /**
     * Метод сохраняет ошибку, произошедшую в сценарии бизнес-логики.
     *
     * @param exception произошедшая ошибка
     */
    public void setException(@Nullable Throwable exception) {
        this.exception = exception;
    }

    /**
     * Определяет, содержит ли ошибку результат работы сценария бизнес-логики.
     *
     * @return {@code true} если произошла ошибка в работе сценария
     */
    public boolean hasException() {
        return exception != null;
    }

    /**
     * Метод возвращает ошибку, произошедшую в работе сценария бизнес-логики (если есть).
     *
     * @return произошедшая ошибка (или {@code null})
     */
    @Nullable
    public Throwable getException() {
        return exception;
    }

    /**
     * Задает текущее состояние сценария бизнес-логики.
     *
     * @param status текущее состояние (не может быть {@code null})
     */
    protected void setStatus(@NonNull Status status) {
        this.status = status;
    }

    /**
     * Метод возвращает текущее состояние сценария бизнес-логики
     *
     * @return текущее состояние
     */
    @NonNull
    public Status getStatus() {
        return status;
    }
}
