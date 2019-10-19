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
